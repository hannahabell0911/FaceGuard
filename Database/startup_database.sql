CREATE TABLE Users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    password_salt VARCHAR(255) NOT NULL,
    face_recognition_1 BOOLEAN DEFAULT FALSE,
);

CREATE TABLE camera_sensor_data (
    data_id SERIAL PRIMARY KEY,
    image_filename VARCHAR(255) NOT NULL,
    capture_date DATE NOT NULL,
    capture_time TIME NOT NULL,
    user_id INT REFERENCES users(user_id), -- Optional: associate data with users
    additional_info TEXT,
    CONSTRAINT unique_capture_data UNIQUE (capture_date, capture_time, location)
);

-- wiating for database organiser to review to add other senor data
