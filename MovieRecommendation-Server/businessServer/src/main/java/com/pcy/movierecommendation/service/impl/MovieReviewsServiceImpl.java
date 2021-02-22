package com.pcy.movierecommendation.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pcy.movierecommendation.core.utils.IdWorkerUtil;
import com.pcy.movierecommendation.dao.MovieReviewsDao;
import com.pcy.movierecommendation.dao.MovieUserRatingsDao;
import com.pcy.movierecommendation.entity.movieReviews.MovieReviews;
import com.pcy.movierecommendation.entity.movieReviews.MovieUserRatings;
import com.pcy.movierecommendation.service.MovieReviewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        List<MovieReviews> movieReviewsList = this.movieReviewsDao.queryByDoubanId(doubanId);
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
        int rowFlag1 = this.movieReviewsDao.insert(movieReviews);
        if (rowFlag1 == 1) {
            logger.info("插入movie_reviews表成功");
        }
        // 再存入movie_user_ratings表
        MovieUserRatings movieUserRatings = new MovieUserRatings();
        movieUserRatings.setReviewId(String.valueOf(nextId));
        movieUserRatings.setDoubanId(movieReviews.getDoubanId());
        movieUserRatings.setUserUniqueName(movieReviews.getUserUniqueName());
        movieUserRatings.setUserMovieRating((double) (movieReviews.getUserMovieRating() / 10));
        int rowFlag2 = this.movieUserRatingsDao.insert(movieUserRatings);
        if (rowFlag2 == 1) {
            logger.info("插入movie_user_ratings表成功");
        }
        return movieReviews;
    }

    /**
     * 用户给评论点赞
     *
     * @param reviewId 评论id
     * @return 评论点赞是否成功
     */
    @Override
    public Boolean agree(String reviewId) {
        return this.movieReviewsDao.agree(reviewId);
    }
}