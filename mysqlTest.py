import mysql.connector
from mysql.connector import Error
from dotenv import load_dotenv
import os

load_dotenv()
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
    with cnx.cursor() as cursor:
        selectAllQuery = cursor.execute("SELECT * FROM recognized_faces")
        rows = cursor.fetchall()
        for row in rows:
            print(row)
            
    cnx.close()
    
else:
    print("Connection failed")        
