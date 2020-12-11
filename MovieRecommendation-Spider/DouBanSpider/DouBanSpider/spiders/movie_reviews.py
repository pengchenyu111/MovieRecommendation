import scrapy
import re
import random
import string
import sys

sys.setrecursionlimit(100000)
from bs4 import BeautifulSoup
from copy import deepcopy
from lxml import etree
from DouBanSpider.db_handler import connection, cursor
from DouBanSpider.items import MovieReviews


class MovieReviewsSpider(scrapy.Spider):
    name = 'movie_reviews'
    allowed_domains = ['movie.douban.com']

    sql = "SELECT douban_id FROM subjects WHERE type = 'movie' AND douban_id NOT IN (SELECT douban_id FROM movie_reviews);"
    cursor.execute(sql)
    douban_id_tuple_list = cursor.fetchall()

    def start_requests(self):
        for douban_id_tuple in self.douban_id_tuple_list:
            douban_id = douban_id_tuple[0]
            url = 'https://movie.douban.com/subject/{}/comments?status=P'.format(douban_id)
            print('*' * 10 + url)
            item = MovieReviews()
            item['douban_id'] = douban_id
            yield scrapy.Request(
                url,
                meta={'item': deepcopy(item), 'dont_redirect': True, 'handle_httpstatus_list': [302]},
                dont_filter=True
            )

    def parse(self, response):
        print('开始爬取')
        soup = BeautifulSoup(response.text, 'lxml')
        review_list = soup.find_all(attrs={"class": "comment-item"})
        item = deepcopy(response.meta['item'])
        # 获取电影评论的好评、中评、差评率
        self.get_movie_positive_rate(item, response)
        self.get_movie_general_rate(item, response)
        self.get_movie_negative_rate(item, response)
        if len(review_list) > 0:
            for review in review_list:
                self.get_review_id(item, review)
                self.get_user_id(item, review)
                self.get_user_head_portrait_url(item, review)
                self.get_user_url(item, review)
                self.get_user_name(item, review)
                self.get_user_movie_rating(item, review)
                self.get_user_movie_rating_time(item, review)
                self.get_user_movie_rating_agree(item, review)
                self.get_user_movie_rating_content(item, review)
                yield item
        # 翻页
        url = response.xpath('//a[@data-page="next"]/@href').get()
        if url is not None:
            next_url = 'https://movie.douban.com/subject/%s/comments%s' % (item['douban_id'], url)
            print('**********下一页*************' + next_url)
            yield scrapy.Request(
                url=next_url,
                meta={'item': response.meta['item'], 'dont_redirect': True, 'handle_httpstatus_list': [302]},
                dont_filter=True
            )

    # 获取电影评论好评率
    def get_movie_positive_rate(self, item, response):
        regx = '//input[@value="h"]/following-sibling::span[@class="comment-percent"]/text()'
        data = response.xpath(regx).get()
        if data is not None:
            item['movie_positive_rate'] = data
        else:
            item['movie_positive_rate'] = '0%'
        return item

    # 获取电影评论一般评率
    def get_movie_general_rate(self, item, response):
        regx = '//input[@value="m"]/following-sibling::span[@class="comment-percent"]/text()'
        data = response.xpath(regx).get()
        if data is not None:
            item['movie_general_rate'] = data
        else:
            item['movie_general_rate'] = '0%'
        return item

    # 获取电影评论差评率
    def get_movie_negative_rate(self, item, response):
        regx = '//input[@value="l"]/following-sibling::span[@class="comment-percent"]/text()'
        data = response.xpath(regx).get()
        if data is not None:
            item['movie_negative_rate'] = data
        else:
            item['movie_negative_rate'] = '0%'
        return item

    # 获取评论id
    def get_review_id(self, item, review):
        if review is not None:
            item['review_id'] = review['data-cid']
        else:
            item['review_id'] = ''
        return item

    # 获取用户唯一名字标志，短评上没有id，以此做唯一标识
    def get_user_id(self, item, review):
        data = review.find(attrs={"class": "avatar"}).a['href']
        if len(data) > 0:
            item['user_unique_name'] = re.findall('https://www.douban.com/people/(.*)/', data)[0]
        else:
            item['user_unique_name'] = ''
        return item

    # 获取用户头像链接
    def get_user_head_portrait_url(self, item, review):
        data = review.find(attrs={"class": "avatar"}).a.img['src']
        item['user_head_portrait_url'] = data if len(data) > 0 else ''
        return item

    # 获取用户主页链接
    def get_user_url(self, item, review):
        data = review.find(attrs={"class": "avatar"}).a['href']
        item['user_url'] = data if len(data) > 0 else ''
        return item

    # 获取用户名
    def get_user_name(self, item, review):
        data = review.find(attrs={"class": "avatar"}).a['title']
        item['user_name'] = data if len(data) > 0 else ''
        return item

    # 获取用户评分星级
    def get_user_movie_rating(self, item, review):
        data = \
            review.find(name='span', attrs={"class": "comment-info"}).find(name='span').find_next_sibling(name='span')[
                'class']
        if len(data) > 0:
            # 这里class有多个，所以data是个数组
            rating_info = re.findall('allstar(\d+)', data[0])
            if len(rating_info) > 0:
                item['user_movie_rating'] = rating_info[0]
            else:
                # 如果显示comment-time：表示用户未评分，默认为1星评分
                item['user_movie_rating'] = '10'
        else:
            item['user_movie_rating'] = ''
        return item

    # 获取用户评分时间
    def get_user_movie_rating_time(self, item, review):
        data = review.find(name='span', attrs={"class": "comment-time"})['title']
        item['user_movie_rating_time'] = data if len(data) > 0 else ''
        return item

    # 获取用户评论赞同数
    def get_user_movie_rating_agree(self, item, review):
        data = review.find(name='span', attrs={"class": "votes vote-count"}).string
        item['user_movie_rating_agree'] = data if len(data) > 0 else ''
        return item

    # 获取用户评论内容
    def get_user_movie_rating_content(self, item, review):
        data = review.find(name='p', attrs={"class": "comment-content"}).span.string
        item['user_movie_rating_content'] = data.strip() if len(data) > 0 else ''
        return item
