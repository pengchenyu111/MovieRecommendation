import pymysql
from DouBanSpider.settings import MYSQL_HOST, MYSQL_USERNAME, MYSQL_PASSWORD, MYSQL_DB, MYSQL_DB_CHARSET


def connect_mysql():
    connection = pymysql.connect(host=MYSQL_HOST,
                                 user=MYSQL_USERNAME,
                                 password=MYSQL_PASSWORD,
                                 db=MYSQL_DB,
                                 charset=MYSQL_DB_CHARSET)
    return connection


connection = connect_mysql()
cursor = connection.cursor()


# 在获取完电影简要信息后，通过这里提取并存入豆瓣id
def update_movie_subject():
    sql_1 = "INSERT INTO subjects(douban_id) SELECT douban_id FROM movie_brief_introduction;"
    sql_2 = "UPDATE subjects SET type = 'movie';"
    try:
        row_1 = cursor.execute(sql_1)
        print(str(row_1) + '行记录已插入')
        row_2 = cursor.execute(sql_2)
        print(str(row_2) + '行记录已更新')
        connection.commit()
    except Exception as e:
        connection.rollback()
        print(e)


if __name__ == '__main__':
    update_movie_subject()
