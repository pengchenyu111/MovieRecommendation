import pymysql

# MySQL配置
MYSQL_HOST = '127.0.0.1'
MYSQL_USERNAME = 'root'
MYSQL_PASSWORD = 'PCY90321'
MYSQL_DB = 'movie_recommendation'
MYSQL_DB_CHARSET = 'utf8mb4'

connection = pymysql.connect(
    host=MYSQL_HOST,
    user=MYSQL_USERNAME,
    password=MYSQL_PASSWORD,
    db=MYSQL_DB,
    charset=MYSQL_DB_CHARSET
)