import scrapy
import re
from DouBanSpider.db_handler import connection, cursor
from DouBanSpider.items import IMDbRatings


class ImdbRatingsSpider(scrapy.Spider):
    name = 'IMDb_ratings'
    allowed_domains = ['imdb.com']

    sql = "SELECT douban_id,imdb_url FROM movie_detail WHERE douban_id NOT IN (SELECT douban_id FROM imdb_ratings) AND imdb_url IS NOT NULL;"
    cursor.execute(sql)
    tuple_list = cursor.fetchall()

    def start_requests(self):
        for temp in self.tuple_list:
            imdb_id = re.findall('https://www.imdb.com/title/(.*)', temp[1])[0]
            douban_id = temp[0]
            url = 'https://www.imdb.com/title/{}/ratings?ref_=tt_ql_op_4'.format(imdb_id)
            item = IMDbRatings()
            # 获取豆瓣id和IMDb id
            item['douban_id'] = douban_id
            item['imdb_id'] = imdb_id
            # 这里后面可能会加Cookie
            yield scrapy.Request(
                url,
                meta={'item': item, 'dont_redirect': True, 'handle_httpstatus_list': [302]},
                dont_filter=True
            )

    def parse(self, response):
        item = response.meta['item']
        self.get_imdb_rating(item, response)
        self.get_rating_scores(item, response)
        self.get_rating_scores_weights(item, response)
        self.get_rating_scores_votes(item, response)
        self.get_age_ratings(item, response)
        self.get_male_ratings(item, response)
        self.get_female_ratings(item, response)
        yield item

    # 获取IMDb评分
    def get_imdb_rating(self, item, response):
        regx = '//div[@name="ir"]/span/text()'
        data = response.xpath(regx).get()
        if data is None:
            data = '0.0'
        item['imdb_rating'] = data
        return item

    # 获取IMDb评分分级列表
    def get_rating_scores(self, item, response):
        regx = '//*[@id="main"]/section/div/div[3]/div/table[1]//tr/td[1]/div/div/text()'
        item['rating_scores'] = '|'.join(response.xpath(regx).getall())
        return item

    # 获取各级评分权重，|分割
    def get_rating_scores_weights(self, item, response):
        regx = '//*[@id="main"]/section/div/div[3]/div/table[1]//tr/td[2]//div[@class="topAligned"]/text()'
        str_list = response.xpath(regx).getall()
        data = []
        for str in str_list:
            str = str.strip()
            data.append(str)
        item['rating_scores_weights'] = '|'.join(data)
        return item

    # 获取各级评分票数，|分割
    def get_rating_scores_votes(self, item, response):
        regx = '//*[@id="main"]/section/div/div[3]/div/table[1]//tr/td[3]/div/div/text()'
        votes_list = response.xpath(regx).getall()
        data = []
        for votes in votes_list:
            data.append(votes.replace(',', ''))
        item['rating_scores_votes'] = '|'.join(data)
        return item

    # 获取各年龄段评分情况，评分|投票数
    def get_age_ratings(self, item, response):
        for i in range(2, 7):
            regx_score = '//*[@id="main"]/section/div/div[3]/div/table[2]//tr[2]/td[{}]/div[1]/text()'.format(i)
            regx_votes = '//*[@id="main"]/section/div/div[3]/div/table[2]//tr[2]/td[{}]/div[2]/a/text()'.format(i)
            score = response.xpath(regx_score).get()
            votes = response.xpath(regx_votes).get()
            if score is None:
                score = '0'
                votes = '0'
            else:
                votes = votes.strip().replace(',', '')
            if i == 2:
                item['age_all'] = score + '|' + votes
            elif i == 3:
                item['age_less_than_18'] = score + '|' + votes
            elif i == 4:
                item['age_18_29'] = score + '|' + votes
            elif i == 5:
                item['age_30_44'] = score + '|' + votes
            elif i == 6:
                item['age_more_than_45'] = score + '|' + votes
        return item

    # 获取男性投票数
    def get_male_ratings(self, item, response):
        regx_score = '//*[@id="main"]/section/div/div[3]/div/table[2]//tr[3]/td[2]/div[1]/text()'
        regx_votes = '//*[@id="main"]/section/div/div[3]/div/table[2]//tr[3]/td[2]/div[2]/a/text()'
        score = response.xpath(regx_score).get()
        votes = response.xpath(regx_votes).get()
        if score is None:
            score = '0'
            votes = '0'
        else:
            votes = votes.strip().replace(',', '')
        item['male_ratings'] = score + '|' + votes
        return item

    # 获取女性投票数
    def get_female_ratings(self, item, response):
        regx_score = '//*[@id="main"]/section/div/div[3]/div/table[2]//tr[4]/td[2]/div[1]/text()'
        regx_votes = '//*[@id="main"]/section/div/div[3]/div/table[2]//tr[4]/td[2]/div[2]/a/text()'
        score = response.xpath(regx_score).get()
        votes = response.xpath(regx_votes).get()
        if score is None:
            score = '0'
            votes = '0'
        else:
            votes = votes.strip().replace(',', '')
        item['female_ratings'] = score + '|' + votes
        return item
