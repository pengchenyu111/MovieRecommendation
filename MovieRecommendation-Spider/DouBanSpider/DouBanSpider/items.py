# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy


class DoubanspiderItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    pass


# 电影简要信息，主要是拿到其豆瓣id
class MovieBriefIntroduction(scrapy.Item):
    douban_id = scrapy.Field()  # 豆瓣id
    title = scrapy.Field()  # 电影标题
    rate = scrapy.Field()  # 评分
    star = scrapy.Field()  # 用户评分大致区间
    directors = scrapy.Field()  # 导演，列表
    casts = scrapy.Field()  # 演员，列表
    url = scrapy.Field()  # 电影豆瓣链接
    cover = scrapy.Field()  # 电影海报链接
    cover_x = scrapy.Field()  # 电影海报x长度
    cover_y = scrapy.Field()  # 电影海报y长度
