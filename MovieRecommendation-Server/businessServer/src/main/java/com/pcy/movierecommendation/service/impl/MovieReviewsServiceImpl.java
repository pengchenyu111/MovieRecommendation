package com.pcy.movierecommendation.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pcy.movierecommendation.core.constants.MessageConstant;
import com.pcy.movierecommendation.core.utils.DateFormatUtil;
import com.pcy.movierecommendation.core.utils.IdWorkerUtil;
import com.pcy.movierecommendation.core.utils.RedisUtil;
import com.pcy.movierecommendation.dao.MovieReviewsDao;
import com.pcy.movierecommendation.dao.MovieUserDao;
import com.pcy.movierecommendation.dao.MovieUserRatingsDao;
import com.pcy.movierecommendation.entity.movieReviews.MovieReviews;
import com.pcy.movierecommendation.entity.movieReviews.MovieUserRatings;
import com.pcy.movierecommendation.service.MovieReviewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MovieReviews)表服务实现类
 *
 * @author PengChenyu
 * @since 2020-12-29 20:08:19
 */
@Service("movieReviewsService")
public class MovieReviewsServiceImpl implements MovieReviewsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private MovieReviewsDao movieReviewsDao;
    @Resource
    private MovieUserRatingsDao movieUserRatingsDao;
    @Resource
    private MovieUserDao movieUserDao;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    private static final int DEFAULT_REDIS_DB = 0;
    private static final int DEFAULT_RATING_K = 20;

    /**
     * 通过ID查询单条数据
     *
     * @param reviewId 主键
     * @return 实例对象
     */
    @Override
    public MovieReviews queryById(String reviewId) {
        return this.movieReviewsDao.queryById(reviewId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<MovieReviews> queryAllByLimit(int offset, int limit) {
        return this.movieReviewsDao.queryAllByLimit(offset, limit);
    }


    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    @Override
    public int count() {
        return this.movieReviewsDao.count();
    }


    /**
     * 新增数据
     *
     * @param movieReviews 实例对象
     * @return 实例对象
     */
    @Override
    public MovieReviews insert(MovieReviews movieReviews) {
        this.movieReviewsDao.insert(movieReviews);
        return movieReviews;
    }

    /**
     * 修改数据
     *
     * @param movieReviews 实例对象
     * @return 实例对象
     */
    @Override
    public MovieReviews update(MovieReviews movieReviews) {
        this.movieReviewsDao.update(movieReviews);
        return this.queryById(movieReviews.getReviewId());
    }

    /**
     * 通过主键删除数据
     *
     * @param reviewId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String reviewId) {
        return this.movieReviewsDao.deleteById(reviewId) > 0;
    }

    /**
     * 根据doubanId分页查询
     *
     * @param doubanId 豆瓣id
     * @param pageNum  当前页
     * @param pageSize 每页多少数据
     * @return 分页数据
     */
    @Override
    public PageInfo<MovieReviews> queryByDoubanIdPage(Integer doubanId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MovieReviews> movieReviewsList = this.movieReviewsDao.queryByDoubanIdWithSort(doubanId);
        return new PageInfo<>(movieReviewsList);
    }

    /**
     * 用户评论
     *
     * @param movieReviews 评论信息
     * @return 评论信息
     */
    @Override
    public MovieReviews review(MovieReviews movieReviews) {
        // 先存入movie_reviews表
        // 设置reviewId
        IdWorkerUtil idWorkerUtil = new IdWorkerUtil();
        long nextId = idWorkerUtil.nextId();
        logger.info("=====>" + nextId);
        movieReviews.setReviewId(String.valueOf(nextId));
        // 设置评分
        movieReviews.setUserMovieRating(movieReviews.getUserMovieRating() * 10);
        // 设置评分时间
        movieReviews.setUserMovieRatingTime(DateFormatUtil.getNowTime());
        // 用户个人信息和评分时间在前端有保存，由前端传入到movieReviews对象中
        int rowFlag1 = this.movieReviewsDao.insert(movieReviews);
        if (rowFlag1 == 1) {
            logger.info("插入movie_reviews表成功");
        }
        // 再存入movie_user_ratings表
        MovieUserRatings movieUserRatings = new MovieUserRatings();
        movieUserRatings.setReviewId(String.valueOf(nextId));
        movieUserRatings.setDoubanId(movieReviews.getDoubanId());
        Integer userId = movieUserDao.queryByUserUniqueName(movieReviews.getUserUniqueName()).getUserId();
        movieUserRatings.setUserId(userId);
        movieUserRatings.setUserMovieRating((double) (movieReviews.getUserMovieRating() / 10));
        movieUserRatings.setUserMovieRatingTime(DateFormatUtil.getNowTime());
        int rowFlag2 = this.movieUserRatingsDao.insert(movieUserRatings);
        if (rowFlag2 == 1) {
            logger.info("插入movie_user_ratings表成功");
        }
        // 再更新redis中的用户最近k次评分数据
        String key = "rec:rating:userId:" + userId;
        // 如果redis中没有之前的评分数据，还得先存入之前的数据
        if (!redisUtil.exists(key)) {
            List<MovieUserRatings> movieUserRatingsList = this.movieUserRatingsDao.queryByUserId(userId);
            String[] data = movieUserRatingsList.stream()
                    .map(x -> x.getDoubanId() + ":" + x.getUserMovieRating())
                    .toArray(String[]::new);
            Long ratingCount = redisUtil.lpush(DEFAULT_REDIS_DB, key, data);
            logger.info(String.format("[将用户最近的评分数据存入Redis]-用户%d-历史评分数%d", userId, ratingCount));
        } else {
            String value = movieUserRatings.getDoubanId() + ":" + movieUserRatings.getUserMovieRating();
            Long valueCountInList = redisUtil.lpush(DEFAULT_REDIS_DB, key, value);
            logger.info("[更新Redis中用户最近K次评分成功]-" + key + "[队列中个数为]-" + valueCountInList);
        }

        // 发送消息给Kafka
        StringBuilder builder = new StringBuilder();
        String message = builder.append(userId).append("|")
                .append(movieUserRatings.getDoubanId()).append("|")
                .append(movieUserRatings.getUserMovieRating()).append("|")
                .append(movieUserRatings.getUserMovieRatingTime()).toString();
        kafkaTemplate.send(MessageConstant.TOPIC_MOVIE_REC_SYS_RATING, message)
                .addCallback(success -> {
                            logger.info(String.format("[发送评分消息成功]-%s", message));
                        }, failure -> {
                            logger.info(String.format("[发送评分消息失败]-%s", message));
                        }
                );
        return movieReviews;
    }

    /**
     * 用户给评论点赞
     *
     * @param reviewId 评论id
     * @return 修改后的数据
     */
    @Override
    public MovieReviews agree(String reviewId) {
        this.movieReviewsDao.agree(reviewId);
        return this.movieReviewsDao.queryById(reviewId);
    }

    /**
     * 获取用户最近的K次评分数据
     *
     * @param userId 用户id
     * @param k      数据量
     * @return 数据列表
     */
    @Override
    public List<MovieReviews> kRecentRatings(Integer userId, Integer k) {
        return this.movieReviewsDao.kRecentRatings(userId, k);
    }

    /**
     * 根据userId分页查询用户历史评论
     *
     * @param userId   用户id
     * @param pageNum  当前页
     * @param pageSize 每页多少数据
     * @return 分页数据
     */
    @Override
    public PageInfo<MovieReviews> userHistoryReviews(Integer userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MovieReviews> movieReviewsList = this.movieReviewsDao.userHistoryReviews(userId);
        return new PageInfo<>(movieReviewsList);
    }
}