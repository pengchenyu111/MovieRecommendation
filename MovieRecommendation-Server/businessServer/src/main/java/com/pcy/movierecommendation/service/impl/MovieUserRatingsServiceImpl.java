package com.pcy.movierecommendation.service.impl;

import com.alibaba.fastjson.JSON;
import com.pcy.movierecommendation.core.utils.RedisUtil;
import com.pcy.movierecommendation.dao.MovieUserRatingsDao;
import com.pcy.movierecommendation.entity.movieReviews.MovieUserRatings;
import com.pcy.movierecommendation.service.MovieUserRatingsService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (MovieUserRatings)表服务实现类
 *
 * @author PengChenyu
 * @since 2020-12-30 16:25:24
 */
@Service("movieUserRatingsService")
public class MovieUserRatingsServiceImpl implements MovieUserRatingsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private MovieUserRatingsDao movieUserRatingsDao;
    @Resource
    RedisUtil redisUtil;

    private static final int DEFAULT_REDIS_DB = 0;
    private static final int DEFAULT_REDIS_RANG_START = 0;

    /**
     * 通过ID查询单条数据
     *
     * @param reviewId 主键
     * @return 实例对象
     */
    @Override
    public MovieUserRatings queryById(String reviewId) {
        return this.movieUserRatingsDao.queryById(reviewId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<MovieUserRatings> queryAllByLimit(int offset, int limit) {
        return this.movieUserRatingsDao.queryAllByLimit(offset, limit);
    }


    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    @Override
    public int count() {
        return this.movieUserRatingsDao.count();
    }


    /**
     * 新增数据
     *
     * @param movieUserRatings 实例对象
     * @return 实例对象
     */
    @Override
    public MovieUserRatings insert(MovieUserRatings movieUserRatings) {
        this.movieUserRatingsDao.insert(movieUserRatings);
        return movieUserRatings;
    }

    /**
     * 修改数据
     *
     * @param movieUserRatings 实例对象
     * @return 实例对象
     */
    @Override
    public MovieUserRatings update(MovieUserRatings movieUserRatings) {
        this.movieUserRatingsDao.update(movieUserRatings);
        return this.queryById(movieUserRatings.getReviewId());
    }

    /**
     * 通过主键删除数据
     *
     * @param reviewId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String reviewId) {
        return this.movieUserRatingsDao.deleteById(reviewId) > 0;
    }

    /**
     * 获取用户最近的K次评分数据(简要信息)
     *
     * @param userId 用户id
     * @param k      数据量
     * @return 数据列表
     */
    @Override
    public List<MovieUserRatings> kRecentRatingsShort(Integer userId, Integer k) {
        return movieUserRatingsDao.kRecentRatingsShort(userId, k);
    }

    /**
     * 将用户最近的评分数据存入Redis
     *
     * @param userId 用户id
     * @return 是否成功信息
     */
    @Override
    public Long loadIntoRedis(Integer userId) {
        List<MovieUserRatings> movieUserRatingsList = this.movieUserRatingsDao.queryByUserId(userId);
        String key = "rec:rating:userId:" + userId;
        String[] data = movieUserRatingsList.stream()
                .map(x -> x.getDoubanId() + ":" + x.getUserMovieRating())
                .toArray(String[]::new);
        if (data.length == 0) {
            logger.info(String.format("[将用户最近的评分数据存入Redis]-用户%d无评分数据", userId));
            return -1L;
        }
        // 先清空再存入
        redisUtil.ltrim(key,1,0);
        Long ratingCount = redisUtil.lpush(DEFAULT_REDIS_DB, key, data);
        logger.info(String.format("[将用户最近的评分数据存入Redis]-用户%d-历史评分数%d", userId, ratingCount));
        return ratingCount;
    }
}