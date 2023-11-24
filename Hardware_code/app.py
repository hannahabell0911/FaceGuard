import RPi.GPIO as GPIO
import time

PIR_pin = 23
Buzzer_pin = 24 

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.setup(PIR_pin, GPIO.IN)
GPIO.setup(Buzzer_pin, GPIO.OUT)

def beep():
    GPIO.output(Buzzer_pin, True)
    time.sleep(0.3)
    GPIO.output(Buzzer_pin, False)

def motionDetection():
    while True:
        if GPIO.input(PIR_pin):
            print("Motion detected")
            beep()
        time.sleep(1)

motionDetection()


