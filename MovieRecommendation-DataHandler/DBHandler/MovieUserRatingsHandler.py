from DBHandler.database import connection


# 从评论表中往用户评分表中插入数据，因为评论表太大了，冗余字段过多，不利于后面的推荐算法
def insert_data():
    sql = "INSERT INTO movie_user_ratings(review_id,douban_id,user_unique_name, user_movie_rating) SELECT review_id,douban_id,user_unique_name, movie_reviews.user_movie_rating / 10 FROM movie_reviews;"
    try:
        cursor = connection.cursor()
        rows = cursor.execute(sql)
        print('{}行记录已插入'.format(rows))
        connection.commit()
    except Exception as e:
        print(e)
        connection.rollback()
    finally:
        print('数据库连接已关闭')
        connection.close()


if __name__ == '__main__':
    insert_data()
