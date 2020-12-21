from DBHandler.database import connection

cursor = connection.cursor()


def img_list_handler():
    sql = 'SELECT douban_id,img_list FROM movie_detail;'
    try:
        cursor.execute(sql)
        img_list_tuples = cursor.fetchall()
        for img_list_tuple in img_list_tuples:
            douban_id = img_list_tuple[0]
            str = img_list_tuple[1]
            if str is not None:
                if str[0] == '(' and str[-1] == ')':
                    str = str[1:-1]
                    sql2 = "UPDATE movie_detail SET img_list = '{}' WHERE douban_id = {};".format(str, douban_id)
                    cursor.execute(sql2)
                    print('{}已修改'.format(douban_id))
                    connection.commit()
    except Exception as e:
        print(e)
        connection.rollback()
    finally:
        print('数据库连接已关闭')
        connection.close()


if __name__ == '__main__':
    img_list_handler()
