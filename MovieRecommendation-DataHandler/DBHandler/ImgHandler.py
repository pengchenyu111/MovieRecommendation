import requests
from DBHandler.database import connection
import re

cursor = connection.cursor()
base_url = 'C:/Users/PENGZI/Desktop/poster/'
base_url_2 = 'C:/Users/PENGZI/Desktop/imgs/'
nginx_base_url = 'http://81.70.252.155:8000/movierecommendation/movie/'


def movie_poster_handler():
    sql = 'SELECT douban_id,cover_url FROM movie_detail;'
    try:
        cursor.execute(sql)
        img_tuples = cursor.fetchall()
        for img_tuple in img_tuples:
            douban_id = img_tuple[0]
            old_url = img_tuple[1]
            new_filename = base_url + str(douban_id) + '.jpg'
            html = requests.get(old_url)
            with open(new_filename, "wb")as f:
                f.write(html.content)
            print('{}已保存'.format(douban_id))
    except Exception as e:
        print(e)
        connection.rollback()
    finally:
        print('数据库连接已关闭')
        connection.close()


def change_movie_poster_url():
    sql = 'SELECT douban_id FROM movie_detail;'
    update_sql1 = "update movie_detail set cover_url = '{}' where douban_id = {}"
    update_sql2 = "update movie_brief_introduction set cover = '{}' where douban_id = {}"
    try:
        cursor.execute(sql)
        img_tuples = cursor.fetchall()
        for img_tuple in img_tuples:
            douban_id = img_tuple[0]
            new_url = nginx_base_url + 'poster/' + str(douban_id) + '.jpg'
            cursor.execute(update_sql1.format(new_url, douban_id))
            print('movie_detail表{}已修改'.format(douban_id))
            cursor.execute(update_sql2.format(new_url, douban_id))
            print('movie_brief_introduction表{}已修改'.format(douban_id))
            connection.commit()
    except Exception as e:
        print(e)
        connection.rollback()
    finally:
        print('数据库连接已关闭')
        connection.close()


def movie_img_handler():
    sql = 'SELECT douban_id,img_list FROM movie_detail where img_list is not null;'
    try:
        cursor.execute(sql)
        img_tuples = cursor.fetchall()
        for img_tuple in img_tuples:
            douban_id = img_tuple[0]
            old_url_str = img_tuple[1]
            old_url_list = old_url_str.split(",")
            counter = 0
            for old_url in old_url_list:
                counter += 1
                new_filename = base_url_2 + str(douban_id) + '-' + str(counter) + '.jpg'
                html = requests.get(old_url)
                with open(new_filename, "wb")as f:
                    f.write(html.content)
                print('{}已保存'.format(new_filename))
    except Exception as e:
        print(e)
        connection.rollback()
    finally:
        print('数据库连接已关闭')
        connection.close()


def change_movie_img_url():
    sql = 'SELECT douban_id,img_list FROM movie_detail where img_list is not null;'
    update_sql = "update movie_detail set img_list = '{}' where douban_id = {}"
    try:
        cursor.execute(sql)
        img_tuples = cursor.fetchall()
        for img_tuple in img_tuples:
            douban_id = img_tuple[0]
            old_url_str = img_tuple[1]
            old_url_list = old_url_str.split(",")
            date = []
            for i in range(len(old_url_list)):
                date.append(nginx_base_url + 'imgs/' + str(douban_id) + '-' + str(i + 1) + '.jpg')
            new_img_list = ",".join(date)
            cursor.execute(update_sql.format(new_img_list, douban_id))
            connection.commit()
            print('{}已修改'.format(douban_id))
    except Exception as e:
        print(e)
        connection.rollback()
    finally:
        print('数据库连接已关闭')
        connection.close()


if __name__ == '__main__':
    # change_movie_poster_url()
    change_movie_img_url()
