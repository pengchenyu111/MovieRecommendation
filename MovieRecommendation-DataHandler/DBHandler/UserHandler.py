from DBHandler.database import connection


# 从评论表中往用户表中插入数据
def insert_data():
    sql = '''
    INSERT INTO movie_user ( user_unique_name, user_name, user_head_portrait_url, user_url ) SELECT
    any_value ( user_unique_name ) AS user_unique_name,
    any_value ( user_name ) AS user_name,
    any_value ( user_head_portrait_url ) AS user_head_portrait_url,
    any_value ( user_url ) AS user_url 
    FROM
	    movie_reviews 
    GROUP BY
	    user_unique_name ;'''
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
