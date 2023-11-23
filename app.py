from flask import Flask, send_file, jsonify
from camera import capture_image
import gpiozero
from picamera import PiCamera
from datetime import datetime
import face_recognition
import mysql.connector
from mysql.connector import Error
from dotenv import load_dotenv
import mysql.connector
from mysql.connector import Error
import os
import numpy as np
import json
from gtts import gTTS
import pygame
import time

pygame.mixer.init()

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

cnx = mysql.connector.connect(**config)


if cnx.is_connected():
    print("Connected to MySQL database")
    #with cnx.cursor() as cursor:
    #    selectAllQuery = cursor.execute("SELECT * FROM recognized_faces")
    #    rows = cursor.fetchall()
    #    for row in rows:
    #        print(row)
            
    cnx.close()
    
else:
    print("Connection failed")
    
ringButton = gpiozero.Button(17)
camera = PiCamera()    

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
                message = f"Face matches with {name}"
                print(message)
            
                tts = gTTS(text=message, lang='en')
                tts.save("match.mp3")
            
                pygame.mixer.music.load("match.mp3")
                pygame.mixer.music.play()
                while pygame.mixer.music.get_busy():
                    time.sleep(1)
                match_found = True
                break
        if not match_found:
            print(f'No match found for unknown face {i}')
        
    
app = Flask(__name__)
    
@app.route('/')
def hello_world():
    return 'Hello, World!'

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
    