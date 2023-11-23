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
    with cnx.cursor() as cursor:
        selectAllQuery = cursor.execute("SELECT * FROM recognized_faces")
        rows = cursor.fetchall()
        for row in rows:
            print(row)
            
    cnx.close()
    
else:
    print("Connection failed")        
