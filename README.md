# FaceGuard: Backend 📷🔒
![Logo](https://i.ibb.co/NYrGB0V/Faceguard-1-1.png)

    

FaceGuard is a smart doorbell system that lets it's users customize the way they interact with their guests. FaceGuard's open sourced backend code can be found on this GitHub. 
## Features ✨

- Facial Recognition 🧑
- Customizable Speech Greeting 🗣️
- Visitor History 🚶‍♂️
- Easy To Use Mobile App 📱


## Demo 📽️

You can find a demo video of the project below:


https://github.com/hannahabell0911/FaceGuard/assets/96305332/db29987b-9944-4169-b47a-6fe082cdaa4e



## Deployment 💻

To deploy this project:
    
- Set up a MySQL database
- Create two tables, `recognized_faces` and `visitor_history`
- Insert `id`, `name`, `face_encoding` columns into `recognized_faces`
- Insert `id`, `name`, `timestamp` columns into `visitor_history`
- Set up a PubNub account and grab a `publish_key` and a `subscribe_key``
- Connect all your hardware to your device
- Clone this code and run these commands to install required libraries:

    ```
    pip install flask 
    pip install gpiozero
    pip install picamera
    pip install face_recognition
    pip install mysql.connector
    pip install dotenv
    pip install numpy
    pip install gtts
    pip install pygame
    pip install pubnub
    pip install camera
    pip install datetime
    pip install mysql
    pip install uuid

    ```
- Create a .env file in the project directory and set it up with these parameteres:

    -    `USER` Username that's registered on the database

    -    `PASSWORD` Password of the user created on database

    -    `HOST` IP address of the database host

    -    `DATABASE` Name of the database

    -    `PUBNUB_PUBLISH_KEY` PubNub publish key

    -    `PUBNUB_SUBSCRIBE_KEY` PubNub subscribe key

- Run the project by opening a terminal and entering:
    ```
    python app.py
    ```

Please check back here often for updates. Database columns or any other step might change. This project is still a work in progress, thanks for your understanding! 🙏    
## Roadmap 🗺️

- More detailed databases

- Motion sensor functionality

- More user-friendly customization options


## Project Members 👩‍🏫
- [@anilatici](https://github.com/anilatici) - Backend Developer and Database Manager
- [@hannahabell0911](https://github.com/hannahabell0911) - Frontend Developer, Database Manager and Team Leader
- [@Sanid1708](https://github.com/Sanid1707) - Frontend Developer, Hardware
- [@tiao1314](https://github.com/tiao1314) - Hardware
- [@Martinz7z](https://github.com/Martinz7z) - User Testing
## License 📖

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)

