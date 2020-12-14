import scrapy
from DouBanSpider.db_handler import connection, cursor
from copy import deepcopy


class MovieImgsSpider(scrapy.Spider):
    name = 'movie_imgs'
    allowed_domains = ['movie.douban.com']

    sql = "SELECT douban_id FROM movie_detail WHERE movie_detail.img_list IS NULL;"
    cursor.execute(sql)
    douban_id_tuple_list = cursor.fetchall()

    def start_requests(self):
        for douban_id_tuple in self.douban_id_tuple_list:
            douban_id = douban_id_tuple[0]
            url = 'https://movie.douban.com/subject/{}/all_photos'.format(douban_id)
            print('*' * 10 + url)
            item = {'douban_id': douban_id}
            yield scrapy.Request(
                url,
                meta={'item': deepcopy(item), 'dont_redirect': True, 'handle_httpstatus_list': [302]},
                dont_filter=True
            )

    def parse(self, response):
        item = deepcopy(response.meta['item'])
        regx = '//div[@class="bd"]/ul/li/a/img/@src'
        data = response.xpath(regx).getall()
        if data is not None:
            if len(data) >= 10:
                item['img_list'] = ','.join(data[0:10])
            else:
                item['img_list'] = ','.join(data)
        yield item
