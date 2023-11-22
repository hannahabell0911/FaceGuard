from flask import Flask, send_file, jsonify
from camera import capture_image
import gpiozero
from picamera import PiCamera
from datetime import datetime
import face_recognition
import mysql.connector
from mysql.connector import Error

import mysql.connector
from mysql.connector import Error

config = {
    'host': '13.48.48.253',
    'database': 'faceguard',
    'user': 'anilatici',
    'password': 'Anilatici01.'
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
    
app = Flask(__name__)
    
@app.route('/')
def hello_world():
    return 'Hello, World!'

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
    