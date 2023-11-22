from flask import Flask, send_file, jsonify
from camera import capture_image
from storage import get_image_list

app = Flask(__name__)

@app.route('/')
def hello_world():
    return 'Hello, World!'

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
    