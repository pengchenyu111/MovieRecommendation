import re
import jieba
import matplotlib.pyplot as plt
import numpy as np
from wordcloud import WordCloud, ImageColorGenerator, STOPWORDS
from PIL import Image
from DBHandler.database import connection

cursor = connection.cursor()


def get_subjects():
    sql = "SELECT douban_id FROM movie_reviews GROUP BY douban_id;"
    cursor.execute(sql)
    return cursor.fetchall()


def get_reviews_content(douban_id):
    sql = "SELECT user_movie_rating_content from movie_reviews WHERE douban_id = {};".format(douban_id)
    cursor.execute(sql)
    return cursor.fetchall()


def write_into_txt(content_tuple_list):
    with open(file='contents.txt', mode='a+', encoding='utf-8') as f:
        for content_tuple in content_tuple_list:
            data = re.sub('\n', '', content_tuple[0])
            f.write(data + '\n')


def clean_txt():
    with open(file='contents.txt', mode='r+') as f:
        f.truncate(0)


def draw_cloud(text, graph, save_name):
    textfile = open(file=text, encoding='utf-8').read()
    # 中文分词
    wordlist = jieba.cut(textfile, cut_all=True)
    # 连接词语
    space_list = " ".join(wordlist)
    # 背景轮廓图
    backgroud = np.array(Image.open(graph))
    # 停用词集
    stop_words = ['我', '你', '他', '她', '它', '的', '了', '在', '都', '是', '很', '也', '和', '有'] + list(STOPWORDS)
    mywordcloud = WordCloud(background_color="white",  # 背景颜色
                            width=400,
                            height=200,
                            # mask=backgroud,  # 写字用的背景图，从背景图取颜色
                            max_words=100,  # 最大词语数量
                            stopwords=stop_words,  # 停用词
                            font_path="fonts/msyh.ttc",  # 字体
                            max_font_size=100,  # 最大字体尺寸
                            random_state=50,  # 随机角度
                            scale=2,
                            collocations=False,  # 避免重复单词
                            )
    mywordcloud.generate(space_list)  # 生成词云
    ImageColorGenerator(backgroud)  # 生成词云的颜色
    # plt.imshow(mywordcloud)  # 显示词云
    plt.axis("off")  # 关闭保存
    # plt.show()
    mywordcloud.to_file(save_name)


def handler():
    douban_id_tuple_list = get_subjects()
    for douban_id_tuple in douban_id_tuple_list:
        douban_id = douban_id_tuple[0]
        content_tuple_list = get_reviews_content(douban_id)
        write_into_txt(content_tuple_list)
        draw_cloud(text='contents.txt',
                   graph='graph/movie_2.png',
                   save_name='word_cloud_imgs/{}.jpg'.format(douban_id))
        print('{}已绘制完成'.format(douban_id))
        clean_txt()


if __name__ == '__main__':
    handler()
