import pymysql

# MySQL配置
MYSQL_HOST = '81.70.252.155'
MYSQL_USERNAME = 'root'
MYSQL_PASSWORD = 'Pcy90321.'
MYSQL_DB = 'movie_recommendation'
MYSQL_DB_CHARSET = 'utf8mb4'

connection = pymysql.connect(
    host=MYSQL_HOST,
    user=MYSQL_USERNAME,
    password=MYSQL_PASSWORD,
    db=MYSQL_DB,
    charset=MYSQL_DB_CHARSET
)
