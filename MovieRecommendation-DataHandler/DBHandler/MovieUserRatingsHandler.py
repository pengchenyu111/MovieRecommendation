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


# 由于上面的方面少写了个评论时间的字段，不得不重新插入
def insert_timestamp_data():
    sql = '''
        UPDATE movie_user_ratings
        SET movie_user_ratings.user_movie_rating_time = (
            SELECT movie_reviews.user_movie_rating_time FROM movie_reviews WHERE movie_reviews.review_id = movie_user_ratings.review_id)
        WHERE movie_user_ratings.review_id;'''
    try:
        cursor = connection.cursor()
        rows = cursor.execute(sql)
        print('{}行记录已更新'.format(rows))
        connection.commit()
    except Exception as e:
        print(e)
        connection.rollback()
    finally:
        print('数据库连接已关闭')
        connection.close()


# 将表中user_unique_name替换为user_id,之后删除user_unique_name字段
def replace_name_to_id():
    sql = '''
        UPDATE movie_user_ratings a
        INNER JOIN(SELECT movie_user_ratings.review_id, movie_user.user_id FROM movie_user,movie_user_ratings  
            WHERE movie_user_ratings.user_unique_name = movie_user.user_unique_name) b
        ON a.review_id = b.review_id
        SET a.user_id = b.user_id;'''
    try:
        cursor = connection.cursor()
        rows = cursor.execute(sql)
        print('{}行记录已更新'.format(rows))
        connection.commit()
    except Exception as e:
        print(e)
        connection.rollback()
    finally:
        print('数据库连接已关闭')
        connection.close()


if __name__ == '__main__':
    # insert_data()
    # insert_timestamp_data()
    replace_name_to_id()
