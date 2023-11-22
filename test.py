import gpiozero
import picamera
from time import sleep

button = gpiozero.Button(17)
camera = picamera.PiCamera()

def takePic():
    camera.capture('test_image.jpg')
    print('Picture taken')
    
button.when_pressed = takePic

while True:
    sleep(1)
    