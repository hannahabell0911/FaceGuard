import RPi.GPIO as GPIO
import time
import os
import pyttsx3
from datetime import datetime
import subprocess
import logging

PIR_pin = 23
Buzzer_pin = 24
Led_pin = 26

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.setup(PIR_pin, GPIO.IN)
GPIO.setup(Buzzer_pin, GPIO.OUT)
GPIO.setup(Led_pin, GPIO.OUT)


def beep(repeat):
    for _ in range(repeat):
        for pulse in range(60):
            GPIO.output(Buzzer_pin, True)
            time.sleep(0.001)
            GPIO.output(Buzzer_pin, False)
            time.sleep(0.001)
        time.sleep(0.02)

def capture_photo():
    timestamp = datetime.now().strftime("%Y%m%d%H%M%S")
    filename = f"/home/tiao/Desktop/images/MotionImage_{timestamp}.jpg"
    subprocess.run(["fswebcam", "-r", "1280x720", "--no-banner", filename])
    logging.info(f"{timestamp} - Picture captured: {filename}")
    


def motionDetection():
    while True:
        if GPIO.input(PIR_pin):
            print("Motion detected")
            GPIO.output(Led_pin, True)
            beep(4)  
            capture_photo()
            GPIO.output(Led_pin, False)
        time.sleep(1)

motionDetection()
