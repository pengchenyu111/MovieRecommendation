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


# 电影详情信息
class MovieDetail(scrapy.Item):
    douban_id = scrapy.Field()  # 豆瓣id
    title = scrapy.Field()  # 电影名
    brief_instruction = scrapy.Field()  # 电影简介
    directors = scrapy.Field()  # 导演，列表
    screenwriters = scrapy.Field()  # 编剧，列表
    casts = scrapy.Field()  # 演员，列表
    types = scrapy.Field()  # 类型，列表
    production_country_area = scrapy.Field()  # 制片国家/地区
    language = scrapy.Field()  # 语言
    publish_date = scrapy.Field()  # 上映日期，列表
    runtime = scrapy.Field()  # 片长
    rating_score = scrapy.Field()  # 评分分数，10分制
    rating_star = scrapy.Field()  # 评分星级，5星制
    rating_num = scrapy.Field()  # 评分人数
    rating_5_star_weight = scrapy.Field()  # 评5星占比
    rating_4_star_weight = scrapy.Field()  # 评4星占比
    rating_3_star_weight = scrapy.Field()  # 评3星占比
    rating_2_star_weight = scrapy.Field()  # 评2星占比
    rating_1_star_weight = scrapy.Field()  # 评1星占比
    better_than = scrapy.Field()  # 好于其他类型影片占比，列表
    douban_url = scrapy.Field()  # 豆瓣电影链接
    cover_url = scrapy.Field()  # 电影海报链接
    imdb_url = scrapy.Field()  # IMDb链接


# IMDb网站评分信息
class IMDbRatings(scrapy.Item):
    imdb_id = scrapy.Field()  # IMDb id
    douban_id = scrapy.Field()  # 豆瓣id
    imdb_rating = scrapy.Field()  # IMDb评分
    rating_scores = scrapy.Field()  # 各级评分，1-10，|分割
    rating_scores_weights = scrapy.Field()  # 各级评分权重，|分割
    rating_scores_votes = scrapy.Field()  # 各级评分投票数，|分割
    age_all = scrapy.Field()  # 全年龄段评分情况，评分|投票数
    age_less_than_18 = scrapy.Field()  # 年龄小于18的评分情况，评分|投票数
    age_18_29 = scrapy.Field()  # 年龄18-29的评分情况，评分|投票数
    age_30_44 = scrapy.Field()  # 年龄30-44的评分情况，评分|投票数
    age_more_than_45 = scrapy.Field()  # 年龄大于45的评分情况，评分|投票数
    male_ratings = scrapy.Field()  # 男性评分情况
    female_ratings = scrapy.Field()  # 女性评分情况


# 豆瓣短评信息
class MovieReviews(scrapy.Item):
    review_id = scrapy.Field()  # 评论id
    douban_id = scrapy.Field()  # 电影豆瓣id
    user_unique_name = scrapy.Field()  # 用户唯一名字标志，短评上没有id，以此做唯一标识
    user_head_portrait_url = scrapy.Field()  # 用户头像url
    user_url = scrapy.Field()  # 用户主页链接
    user_name = scrapy.Field()  # 用户名
    user_movie_rating = scrapy.Field()  # 用户对电影的评分星级，5星级
    user_movie_rating_time = scrapy.Field()  # 用户对电影的评分时间
    user_movie_rating_agree = scrapy.Field()  # 其他用户对此评论的赞同数
    user_movie_rating_content = scrapy.Field()  # 评论内容
    movie_positive_rate = scrapy.Field()  # 电影评论好评率
    movie_general_rate = scrapy.Field()  # 电影评论一般评率
    movie_negative_rate = scrapy.Field()  # 电影评论差评率
