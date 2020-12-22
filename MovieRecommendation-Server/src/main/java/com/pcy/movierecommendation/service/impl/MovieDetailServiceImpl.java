package com.pcy.movierecommendation.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pcy.movierecommendation.core.utils.RedisUtil;
import com.pcy.movierecommendation.dao.MovieDetailDao;
import com.pcy.movierecommendation.entity.movieDetail.MovieDetail;
import com.pcy.movierecommendation.service.MovieDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MovieDetail)表服务实现类
 *
 * @author PengChenyu
 * @since 2020-12-21 21:41:53
 */
@Service("movieDetailService")
public class MovieDetailServiceImpl implements MovieDetailService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private MovieDetailDao movieDetailDao;
    @Autowired
    RedisUtil redisUtil;

    /**
     * 通过ID查询单条数据
     *
     * @param doubanId 主键
     * @return 实例对象
     */
    @Override
    public MovieDetail queryById(Integer doubanId) {
        String key = "movieDetail:" + doubanId;
        if (redisUtil.exists(key)) {
            String movieDetailJson = redisUtil.get(key);
            logger.info("从Redis中读取" + key);
            return JSON.parseObject(movieDetailJson, MovieDetail.class);
        }
        MovieDetail movieDetail = this.movieDetailDao.queryById(doubanId);
        if (movieDetail != null) {
            String json = JSON.toJSONString(movieDetail);
            redisUtil.set(key, json, 0);
        }
        return movieDetail;
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<MovieDetail> queryAllByLimit(int offset, int limit) {
        return this.movieDetailDao.queryAllByLimit(offset, limit);
    }


    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    @Override
    public int count() {
        return this.movieDetailDao.count();
    }


    /**
     * 新增数据
     *
     * @param movieDetail 实例对象
     * @return 实例对象
     */
    @Override
    public MovieDetail insert(MovieDetail movieDetail) {
        this.movieDetailDao.insert(movieDetail);
        return movieDetail;
    }

    /**
     * 修改数据
     *
     * @param movieDetail 实例对象
     * @return 实例对象
     */
    @Override
    public MovieDetail update(MovieDetail movieDetail) {
        this.movieDetailDao.update(movieDetail);
        return this.queryById(movieDetail.getDoubanId());
    }

    /**
     * 通过主键删除数据
     *
     * @param doubanId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer doubanId) {
        return this.movieDetailDao.deleteById(doubanId) > 0;
    }

    /**
     * 分页查询
     *
     * @param pageNum     当前页
     * @param pageSize    每页多少数据
     * @param movieDetail 查询条件
     * @return 分页数据
     */
    @Override
    public PageInfo<MovieDetail> queryPage(int pageNum, int pageSize, MovieDetail movieDetail) {
        PageHelper.startPage(pageNum, pageSize);
        List<MovieDetail> movieDetailList = movieDetailDao.queryAll(movieDetail);
        return new PageInfo<>(movieDetailList);
    }
}