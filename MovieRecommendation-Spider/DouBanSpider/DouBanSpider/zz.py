import re
from lxml import etree
from bs4 import BeautifulSoup

html3 = '''
<div class="comment-item" data-cid="52241459">
<div class="avatar">
<a href="https://www.douban.com/people/Hamlet-Luang/" title="大时代.歌姬">
<img class="" src="https://img9.doubanio.com/icon/u2763975-164.jpg"/>
</a>
</div>
<div class="comment">
<h3>
<span class="comment-vote">
<span class="votes vote-count">7922</span>
<input type="hidden" value="52241459"/>
<a class="j cls_abnormal" data-id="52241459" href="javascript:;" onclick="">有用</a>
<!-- 删除短评 -->
</span>
<span class="comment-info">
<a class="" href="https://www.douban.com/people/Hamlet-Luang/">大时代.歌姬</a>
<span>看过</span>
<span class="allstar50 rating" title="力荐"></span>
<span class="comment-time" title="2008-08-01 23:04:18">
                    2008-08-01
                </span>
</span>
</h3>
<p class="comment-content">
<span class="short">纸飞机+挥动的小手——那一刻，什么都值了</span>
</p>
</div>
</div>'''
if __name__ == '__main__':
    # review = etree.HTML(html3)
    # print(review.xpath('//div[@class="comment-item"]/@data-cli'))

    list = [1, 2, 3, 4, 5, 6, 7, 8, 9]
    list = list[0:4]
    print(list)