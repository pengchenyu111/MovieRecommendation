# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


# useful for handling different item types with a single interface
from itemadapter import ItemAdapter
from DouBanSpider.items import MovieBriefIntroduction, MovieDetail
import pymysql


class DoubanspiderPipeline:

    # 开启爬虫前，开启MySQL数据库连接
    def open_spider(self, spider):
        self.connection = pymysql.connect(
            host=spider.settings['MYSQL_HOST'],
            user=spider.settings['MYSQL_USERNAME'],
            password=spider.settings['MYSQL_PASSWORD'],
            db=spider.settings['MYSQL_DB'],
            charset=spider.settings['MYSQL_DB_CHARSET']
        )
        self.cursor = self.connection.cursor()

    # item处理入口
    def process_item(self, item, spider):
        if isinstance(item, MovieBriefIntroduction):
            exist = self.get_movie_brief_introduction(item)
            if not exist:
                self.save_movie_brief_introduction(item)
        if isinstance(item, MovieDetail):
            exist = self.get_movie_detail(item)
            if not exist:
                self.save_movie_detail(item)
        return item

    # 获取电影简要信息
    def get_movie_brief_introduction(self, item):
        if item['douban_id'] == '':
            print('获取电影简要信息错误：豆瓣id为空')
        sql = 'SELECT * FROM movie_brief_introduction WHERE douban_id = %s;' % item['douban_id']
        self.cursor.execute(sql)
        return self.cursor.fetchone()

    # 保存电影简要信息进入数据库
    def save_movie_brief_introduction(self, item):
        if item['douban_id'] == '':
            print("保存电影简要信息错误：豆瓣id为空")
        keys = item.keys()
        values = tuple(item.values())
        fields = ','.join(keys)
        temp = ','.join(['%s'] * len(keys))
        sql = 'INSERT INTO movie_brief_introduction (%s) VALUES (%s)' % (fields, temp)
        self.cursor.execute(sql, tuple(i for i in values))
        return self.connection.commit()

    # 获取电影详细信息
    def get_movie_detail(self, item):
        sql = 'SELECT douban_id FROM movie_detail WHERE douban_id = %s;' % item['douban_id']
        self.cursor.execute(sql)
        return self.cursor.fetchone()

    # 保存电影详细信息进入数据库
    def save_movie_detail(self, item):
        keys = item.keys()
        values = tuple(item.values())
        fields = ','.join(keys)
        temp = ','.join(['%s'] * len(keys))
        sql = 'INSERT INTO movie_detail (%s) VALUES (%s)' % (fields, temp)
        self.cursor.execute(sql, tuple(i for i in values))
        return self.connection.commit()
