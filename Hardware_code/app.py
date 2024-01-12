import RPi.GPIO as GPIO
import time
import os
import pyttsx3
from datetime import datetime
import subprocess
import logging

import cv2

from flask import Flask, send_file, jsonify
# from camera import capture_image
import gpiozero
# from picamera import PiCamera
from datetime import datetime
# import face_recognition
import mysql.connector
from mysql.connector import Error
# from dotenv import load_dotenv
import mysql.connector
from mysql.connector import Error
import os
import numpy as np
import json
from gtts import gTTS
import pygame
import time
from pubnub.pnconfiguration import PNConfiguration
from pubnub.pubnub import PubNub
import uuid

PIR_pin = 23
Buzzer_pin = 24
Led_pin = 26

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.setup(PIR_pin, GPIO.IN)
GPIO.setup(Buzzer_pin, GPIO.OUT)
GPIO.setup(Led_pin, GPIO.OUT)

face_cascade = cv2.CascadeClassifier('haarcascade_frontalface_default.xml')

def prompt_user_to_stand():
    speak("Motion detected. Please stand in front of the camera.")


def beep(repeat):
    for _ in range(repeat):
        for pulse in range(60):
            GPIO.output(Buzzer_pin, True)
            time.sleep(0.001)
            GPIO.output(Buzzer_pin, False)
            time.sleep(0.001)
        time.sleep(0.02)

def speak(text):
    engine = pyttsx3.init()
    rate = engine.getProperty('rate')
    engine.setProperty('rate', rate - 50)
    engine.say(text)
    engine.runAndWait()

def capture_photo():
    timestamp = datetime.now().strftime("%Y%m%d%H%M%S")
    filename = f"/home/tiao/Desktop/images/MotionImage_{timestamp}.jpg"
    subprocess.run(["fswebcam", "-r", "1280x720", "--no-banner", filename])
    logging.info(f"{timestamp} - Picture captured: {filename}")


def detect_faces(image_path):
    image = cv2.imread(image_path)
    gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    faces = face_cascade.detectMultiScale(gray, 1.1, 4)
    return len(faces) > 0

def take_picture():
    timestamp = datetime.now().strftime("%Y%m%d%H%M%S")
    filename = f"captured_image_{timestamp}.jpg"
    cap = cv2.VideoCapture(0)
    if not cap.isOpened():
        print("Cannot open camera")
        return None
    time.sleep(3)  # Wait for camera to adjust
    ret, frame = cap.read()
    if not ret:
        print("Can't receive frame (stream end?). Exiting ...")
        return None
    cv2.imwrite(filename, frame)
    cap.release()
    return filename

    
def motionDetection():
    while True:
        if GPIO.input(PIR_pin):
            print("Motion Detected")
            speak("Motion detected")
            GPIO.output(Led_pin, True)
            beep(4)
            prompt_user_to_stand()

            # Wait for a few seconds to give the user time to stand in front of the camera
            time.sleep(10)

            captured_image = take_picture()
            if captured_image and detect_faces(captured_image):
                print("Face Detected")
                speak("Face detected")
                # Your existing code for further processing goes here
            else:
                print("No Face Detected")
                speak("No face detected")
            GPIO.output(Led_pin, False)
        time.sleep(1)

motionDetection()

## backend Code
pygame.mixer.init()
load_dotenv()

random_uuid = str(uuid.uuid4())
pnconfig = PNConfiguration()
pnconfig.uuid = random_uuid
pnconfig.subscribe_key = os.getenv("PUBNUB_SUBSCRIBE_KEY")
pnconfig.publish_key = os.getenv("PUBNUB_PUBLISH_KEY")
pnconfig.ssl = True

pubnub = PubNub(pnconfig)

host = os.getenv("HOST")
user = os.getenv("USER")
password = os.getenv("PASSWORD")
database = os.getenv("DATABASE")

config = {
    'host': host,
    'database': database,
    'user': user,
    'password': password 
}

#cnx = mysql.connector.connect(**config)


#if cnx.is_connected():
    #print("Connected to MySQL database")
    #with cnx.cursor() as cursor:
    #    selectAllQuery = cursor.execute("SELECT * FROM recognized_faces")
    #    rows = cursor.fetchall()
    #    for row in rows:
    #        print(row)
            
    #cnx.close()
    
#else:
    #print("Connection failed")
    
ringButton = gpiozero.Button(17)
camera = PiCamera()    

def publish_message(channel, message):
    pubnub.publish().channel(channel).message(message).sync()

def facialRecognition(image):
    imageToCheck = face_recognition.load_image_file(image)
    faceLocations = face_recognition.face_locations(imageToCheck)
    faceEncodings = face_recognition.face_encodings(imageToCheck, faceLocations)

    connect = mysql.connector.connect(**config)
    cursor = connect.cursor()
    connect.commit()
    cursor.execute("SELECT name, face_encoding from recognized_faces")
    known_encodings = cursor.fetchall()

    for i, unknown_encoding in enumerate(faceEncodings):
        match_found = False
        for (name, known_encoding) in known_encodings:
            known_encoding_array = np.array(json.loads(known_encoding))
            results = face_recognition.compare_faces([known_encoding_array], unknown_encoding)

            if True in results:
                message = f"Greetings {name}, I will be with you in a minute."
                print(message)
            
                tts = gTTS(text=message, lang='en')
                tts.save("match.mp3")
            
                pygame.mixer.music.load("match.mp3")
                pygame.mixer.music.play()
                while pygame.mixer.music.get_busy():
                    time.sleep(1)
                match_found = True
                return name
        if not match_found:
            messageUnrecognized = f"Hello, I will be with you in a minute."
            
            tts = gTTS (text=messageUnrecognized, lang='en')
            tts.save("unrecognized.mp3")
            
            pygame.mixer.music.load("unrecognized.mp3")
            pygame.mixer.music.play()
            while pygame.mixer.music.get_busy():
                time.sleep(1)
                
            print(f'No match found for unknown face {i}')
            return "Unknown"
        
def ring():
    connect1 = mysql.connector.connect(**config)
    cursor1 = connect1.cursor()
    
    timestamp = datetime.now().strftime("%Y-%m-%d_%H.%M.%S")
    file = f"{timestamp}.jpg"
    camera.capture(file)
    
    pygame.mixer.music.load("doorbell.mp3")
    pygame.mixer.music.play()
    while pygame.mixer.music.get_busy():
        time.sleep(1)
        
    name = facialRecognition(file)
    
    doorbellEvent = {
        "timestamp": timestamp,
        "person": name
    }    
    
    sql = "INSERT INTO visitor_history (name, timestamp) VALUES (%s, %s)"
    val = (name, timestamp)
    cursor1.execute(sql, val)
    connect1.commit()
    
    publish_message("doorbell_channel", doorbellEvent)
    
    cursor1.close()
    connect1.close()
    return jsonify(doorbellEvent)           
        
ringButton.when_pressed = ring
    
app = Flask(__name__)
    
@app.route('/')
def hello_world():
    return 'Hello, World!'

@app.route('/ring', methods=['GET'])
def ring_doorbell():
    return ring()

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
    
