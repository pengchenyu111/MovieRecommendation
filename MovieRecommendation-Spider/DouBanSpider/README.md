# MovieRecommendation-Spider

电影推荐系统——爬虫子项目

## 使用许可

该仓库只用于自己的学习及开发记录，**如涉及侵犯个人或团体利益，请与我联系，我将主动撤销一切相关数据，谢谢**！

该仓库中所爬取的一些数据集仅限用于学习研究目的，我不能保证数据的正确性以及任何场景的适用性。对于使用这份数据的其他用户，必须严格遵循下列条件:

1. 未经许可，用户不得将此数据集用于任何商业或收入交易用途。
2. 未经单独许可，用户不得重新转发数据。
3. 用户在使用数据集时，必须声明数据来源。

在任何情况下，我均不对因使用这些数据而造成的任何损失承担责任（包括但不限于数据丢失或数据不准确）。

**如果您有任何其他问题或意见，请发送电子邮件至: iampengchenyu@163.com**



## 技术框架

开发语言：Python	学习地址：https://www.runoob.com/python3/python3-list.html

爬虫框架：Scrapy	官方文档：https://docs.scrapy.org/en/latest/

数据存储：MySQL



## 项目结构

└─DouBanSpider
    │  scrapy.cfg		系统主配置文件
    │
    └─DouBanSpider
        │  db_handler.py		用于处理数据库中的数据集
        │  items.py		数据字典
        │  middlewares.py		中间件（包括了随机UA、代理池等）
        │  pipelines.py		数据采集管道
        │  settings.py		核心配置文件
        │  __init__.py
        │
        ├─spiders		爬虫代码文件夹
        │  │  IMDb_ratings.py		IMDb电影网站上用户的评分情况
        │  │  movie_brief_introduction.py		电影简介
        │  │  movie_detail.py		电影详细信息
        │  │  movie_reviews.py		电影评论信息
        │  │  __init__.py



## 技术要点

1. 随机User-Agent

   在settings.py文件中加入了User-Agent配置池

   在middlewares.py文件中设置了RandomUserAgentMiddleware随机UA中间件，同时在settings中开启该中间件。

2. 代理IP

   鉴于网上的免费IP并不稳定且不高匿名，因此还是购买代理商的动态IP。项目中使用的是**快代理**的**隧道IP**，并不用自己编写切换IP的逻辑，代理商自行切换。本人购买的每次请求切换IP，价格还算公道，稳定性也蛮不错的（有点像打广告……）。

   快代理链接及文档：https://www.kuaidaili.com/pricing/#tps

   在middlewares.py文件中设置了ProxyDownloaderMiddleware动态代理IP中间件，用于切换IP，并在settings中开启该中间件。

   

   ## 数据采集流程

   如果对Python、Scrapy、MySQL不熟悉，建议先学习一下，并且配置好相关环境。

   获取电影简要信息（主要是获取douban_id）

   ```powershell
   cd DouBanSpider
   scrapy crawl movie_brief_introduction
   ```

   提取douban_id

   执行db_handler下的update_movie_subject方法

   获取电影详细信息

   ```powershell
   scrapy crawl movie_detail
   ```

   获取电影评论信息

   ```
   scrapy crawl movie_reviews
   ```

   获取IMDb用户评分信息

   主义IMDb网站是外网，而快代理是不支持国外IP的，所以此时要关闭本项目settings中的代理中间件或使用其他能切换国外IP的代理商。

   ```powershell
   scrapy crawl IMDb_ratings
   ```

   

   ## 数据集

   ### 电影简要信息

   - *douban_id ：豆瓣id*
   - *title ：电影标题*
   - *rate ：评分*
   - *star ： 用户评分大致区间*
   - *directors ： 导演，列表*
   - *casts ：演员，列表*
   - *url ： 电影豆瓣链接*
   - *cover：电影海报链接*
   - *cover_x ：电影海报x长度*
   - *cover_y ： 电影海报y长度*

   ### subjects

   - *douban_id：豆瓣id*
   - *type：类型，movie、music、book*

   ### 电影详细信息

   - *douban_id ：豆瓣id*
   - *title ：电影名*
   - *brief_instruction：电影简介*
   - *directors：导演，列表*
   - *screenwriters ：编剧，列表*
   - *casts：演员，列表*
   - *types ：类型，列表*
   - *production_country_area ：制片国家/地区*
   - *language ：语言*
   - *publish_date：上映日期，列表*
   - *runtime ：片长*
   - *rating_score：评分分数，10分制*
   - *rating_star ：评分星级，5星制*
   - *rating_num ：评分人数*
   - *rating_5_star_weight ：评5星占比*
   - *rating_4_star_weight：评4星占比*
   - *rating_3_star_weight ：评3星占比*
   - *rating_2_star_weight ：评2星占比*
   - *rating_1_star_weight ：评1星占比*
   - *better_than：好于其他类型影片占比，列表*
   - *douban_url ：豆瓣电影链接*
   - *cover_url ：电影海报链接*
   - *imdb_url ：IMDb链接*

   ### 电影评论信息

   - *review_id ：评论id*
   - *douban_id ：电影豆瓣id*
   - *user_unique_name ：用户唯一名字标志，短评上没有id，以此做唯一标识*
   - *user_head_portrait_url ： 用户头像url*
   - *user_url ：用户主页链接*
   - *user_name ：用户名*
   - *user_movie_rating： 用户对电影的评分星级，5星级*
   - *user_movie_rating_time： 用户对电影的评分时间*
   - *user_movie_rating_agree：其他用户对此评论的赞同数*
   - *user_movie_rating_content ： 评论内容*
   - *movie_positive_rate ：电影评论好评率*
   - *movie_general_rate ：电影评论一般评率*
   - *movie_negative_rate ：电影评论差评率*

   ### IMDb评分信息

   - *imdb_id ：IMDb id*
   - *douban_id ：豆瓣id*
   - *imdb_rating ：IMDb评分*
   - *rating_scores ：各级评分，1-10，|分割*
   - *rating_scores_weights ：各级评分权重，|分割*
   - *rating_scores_votes ：各级评分投票数，|分割*
   - *age_all ：全年龄段评分情况，评分|投票数*
   - *age_less_than_18 ：年龄小于18的评分情况，评分|投票数*
   - *age_18_29：年龄18-29的评分情况，评分|投票数*
   - *age_30_44 ： 年龄30-44的评分情况，评分|投票数*
   - *age_more_than_45 ：年龄大于45的评分情况，评分|投票数*
   - *male_ratings ：男性评分情况*
   - *female_ratings： 女性评分情况*



## 后记

如果觉得作者工作不错，请给一个小星星哦！