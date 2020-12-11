import scrapy
import json
from DouBanSpider.items import MovieBriefIntroduction


class MovieBriefIntroductionSpider(scrapy.Spider):
    name = 'movie_brief_introduction'
    allowed_domains = ['movie.douban.com']
    start_urls = ['https://movie.douban.com/j/new_search_subjects?sort=T&range=0,10&tags=&start=0']

    def parse(self, response):
        for i in range(0, 10000, 20):
            url = 'https://movie.douban.com/j/new_search_subjects?sort=U&range=0,10&tags=%E7%94%B5%E5%BD%B1&start={}'.format(i)
            yield scrapy.Request(
                url,
                callback=self.parse_movie_brief_introduction,
                meta={'dont_redirect': True, 'handle_httpstatus_list': [302]},
                dont_filter=True
            )

    def parse_movie_brief_introduction(self, response):
        data_dict = json.loads(response.text)
        movie_list = data_dict['data']
        if len(movie_list) > 0:
            for movie in movie_list:
                movie_brief_introduction = MovieBriefIntroduction()
                movie_brief_introduction['douban_id'] = movie['id']
                movie_brief_introduction['title'] = movie['title']
                movie_brief_introduction['rate'] = movie['rate']
                movie_brief_introduction['star'] = movie['star']
                movie_brief_introduction['directors'] = '|'.join(movie['directors'])
                movie_brief_introduction['casts'] = '|'.join(movie['casts'])
                movie_brief_introduction['url'] = movie['url']
                movie_brief_introduction['cover'] = movie['cover']
                movie_brief_introduction['cover_x'] = movie['cover_x']
                movie_brief_introduction['cover_y'] = movie['cover_y']
                yield movie_brief_introduction
