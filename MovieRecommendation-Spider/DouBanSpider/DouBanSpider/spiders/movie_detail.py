import scrapy
import re
from DouBanSpider.db_handler import connection, cursor
from DouBanSpider.items import MovieDetail


class MovieDetailSpider(scrapy.Spider):
    name = 'movie_detail'
    allowed_domains = ['movie.douban.com']

    sql = "SELECT douban_id FROM subjects WHERE type = 'movie' AND douban_id NOT IN (SELECT douban_id FROM movie_detail);"
    cursor.execute(sql)
    douban_id_tuple_list = cursor.fetchall()
    start_urls = ['https://movie.douban.com/subject/{}/'.format(i[0]) for i in douban_id_tuple_list]

    def start_requests(self):
        for url in self.start_urls:
            # 这里后面可能会加Cookie
            yield scrapy.Request(
                url,
                meta={'main_url': url, 'dont_redirect': True, 'handle_httpstatus_list': [302]},
                dont_filter=True
            )

    def parse(self, response):
        item = MovieDetail()
        self.get_douban_id(item, response)
        self.get_title(item, response)
        self.get_brief_instruction(item, response)
        self.get_directors(item, response)
        self.get_screenwriters(item, response)
        self.get_casts(item, response)
        self.get_types(item, response)
        self.get_production_country_area(item, response)
        self.get_language(item, response)
        self.get_publish_date(item, response)
        self.get_runtime(item, response)
        self.get_rating_score(item, response)
        self.get_rating_star(item, response)
        self.get_rating_num(item, response)
        self.get_rating_star_weight(item, response)
        self.get_better_than(item, response)
        self.get_douban_url(item, response)
        self.get_cover_url(item, response)
        self.get_IMDb_url(item, response)
        yield item

    # 获取豆瓣id
    def get_douban_id(self, item, response):
        main_url = response.meta['main_url']
        item['douban_id'] = main_url.split('subject')[1].split('/')[1]
        return item

    # 获取电影名
    def get_title(self, item, response):
        regx = '//span[@property="v:itemreviewed"]/text()'
        item['title'] = response.xpath(regx).get()
        return item

    # 获取电影简介
    # 可能没有，可能是摘要，可能要展开
    def get_brief_instruction(self, item, response):
        regx_summary = '//div[@id="link-report"]/span[@property="v:summary"]/text()'
        regx_all = '//div[@id="link-report"]/span[@class="all hidden"]/text()'
        str_all = response.xpath(regx_all).get()
        str_summary = response.xpath(regx_summary).get()
        if str_all:
            data = str_all.strip()
        elif str_summary:
            data = str_summary.strip()
        else:
            data = ''
        for i in ['\n', '\u3000']:
            data = re.sub(i, '', data)
        item['brief_instruction'] = data
        return item

    # 获取导演列表
    # 注意返回的是列表, 可能没有
    def get_directors(self, item, response):
        regx = '//div[@id="info"]//a[@rel="v:directedBy"]/text()'
        item['directors'] = ' / '.join(response.xpath(regx).getall())
        return item

    # 获取编剧列表
    # 注意返回的是列表，可能没有
    def get_screenwriters(self, item, response):
        regx = '//div[@id="info"]//span[contains(text(),"编剧")]/following-sibling::*/a/text()'
        item['screenwriters'] = ' / '.join(response.xpath(regx).getall())
        return item

    # 获取主演列表，并不是所有演员
    # 注意返回的是列表，可能没有
    def get_casts(self, item, response):
        regx = '//a[@rel="v:starring"]/text()'
        data = response.xpath(regx).getall()
        if len(data) > 10:
            data = data[0:10]
        item['casts'] = ' / '.join(data)
        return item

    # 获取类型列表
    # 注意返回的是列表，可能没有
    def get_types(self, item, response):
        regx = '//div[@id="info"]/span[@property="v:genre"]/text()'
        item['types'] = ' / '.join(response.xpath(regx).getall())
        return item

    # 获取制片国家/地区
    def get_production_country_area(self, item, response):
        regx = '<span class="pl">制片国家/地区:</span>(.*?)<br/>'
        data = re.findall(regx, response.text)
        if len(data) > 0:
            item['production_country_area'] = data[0].strip()
        else:
            item['production_country_area'] = ''
        return item

    # 获取语言
    def get_language(self, item, response):
        regx = '<span class="pl">语言:</span>(.*?)<br/>'
        data = re.findall(regx, response.text)
        if len(data) > 0:
            item['language'] = data[0].strip()
        else:
            item['language'] = ''
        return item

    # 获取上映日期
    #  注意是列表，可能没有
    def get_publish_date(self, item, response):
        regx = '//div[@id="info"]/span[@property="v:initialReleaseDate"]/text()'
        item['publish_date'] = ' / '.join(response.xpath(regx).getall())
        return item

    # 获取片长
    def get_runtime(self, item, response):
        regx = '//div[@id="info"]/span[@property="v:runtime"]/text()'
        item['runtime'] = response.xpath(regx).get()
        return item

    # 获取豆瓣评分
    def get_rating_score(self, item, response):
        regx = '//div[@id="interest_sectl"]//strong[@property="v:average"]/text()'
        item['rating_score'] = response.xpath(regx).get()
        return item

    # 获取豆瓣评分星级
    def get_rating_star(self, item, response):
        regx = '//*[@id="interest_sectl"]/div[1]/div[2]/div/div[1]/@class'
        str = response.xpath(regx).get()
        if str is not None:
            data = re.findall('ll bigstar bigstar(\d+)', str)
            item['rating_star'] = data[0]
        else:
            item['rating_star'] = 0
        return item

    # 获取评分人数
    def get_rating_num(self, item, response):
        regx = '//div[@class="rating_sum"]/a[@class="rating_people"]/span/text()'
        item['rating_num'] = response.xpath(regx).get()
        return item

    # 获取评分星级比例权重
    def get_rating_star_weight(self, item, response):
        regx = '//div[@class="ratings-on-weight"]/div/span[@class="rating_per"]/text()'
        rating_weights = response.xpath(regx).getall()
        if len(rating_weights) > 0:
            item['rating_5_star_weight'] = rating_weights[0]
            item['rating_4_star_weight'] = rating_weights[1]
            item['rating_3_star_weight'] = rating_weights[2]
            item['rating_2_star_weight'] = rating_weights[3]
            item['rating_1_star_weight'] = rating_weights[4]
        else:
            if len(rating_weights) > 0:
                item['rating_5_star_weight'] = 0
                item['rating_4_star_weight'] = 0
                item['rating_3_star_weight'] = 0
                item['rating_2_star_weight'] = 0
                item['rating_1_star_weight'] = 0
        return item

    # 获取好于其他电影比例
    # 列表，注意可能没有
    def get_better_than(self, item, response):
        regx = '//div[@class="rating_betterthan"]/a/text()'
        item['better_than'] = ' / '.join(response.xpath(regx).getall())
        return item

    # 获取豆瓣电影链接
    def get_douban_url(self, item, response):
        main_url = response.meta['main_url']
        item['douban_url'] = main_url
        return item

    # 获取电影海报链接
    def get_cover_url(self, item, response):
        regx = '//div[@id="mainpic"]/a/img/@src'
        item['cover_url'] = response.xpath(regx).get()
        return item

    # 获取IMDb链接
    def get_IMDb_url(self, item, response):
        regx = '//div[@id="info"]/span[contains(text(),"IMDb链接:")]/following-sibling::a/@href'
        item['imdb_url'] = response.xpath(regx).get()
        return item
