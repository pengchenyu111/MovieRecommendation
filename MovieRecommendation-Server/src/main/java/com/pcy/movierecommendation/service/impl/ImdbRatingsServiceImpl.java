package com.pcy.movierecommendation.service.impl;

import com.pcy.movierecommendation.dao.ImdbRatingsDao;
import com.pcy.movierecommendation.entity.imdbRatings.ImdbRatings;
import com.pcy.movierecommendation.service.ImdbRatingsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (ImdbRatings)表服务实现类
 *
 * @author PengChenyu
 * @since 2020-12-27 22:28:28
 */
@Service("imdbRatingsService")
public class ImdbRatingsServiceImpl implements ImdbRatingsService {
    @Resource
    private ImdbRatingsDao imdbRatingsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param imdbId 主键
     * @return 实例对象
     */
    @Override
    public ImdbRatings queryById(String imdbId) {
        return this.imdbRatingsDao.queryById(imdbId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ImdbRatings> queryAllByLimit(int offset, int limit) {
        return this.imdbRatingsDao.queryAllByLimit(offset, limit);
    }


    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    @Override
    public int count() {
        return this.imdbRatingsDao.count();
    }


    /**
     * 新增数据
     *
     * @param imdbRatings 实例对象
     * @return 实例对象
     */
    @Override
    public ImdbRatings insert(ImdbRatings imdbRatings) {
        this.imdbRatingsDao.insert(imdbRatings);
        return imdbRatings;
    }

    /**
     * 修改数据
     *
     * @param imdbRatings 实例对象
     * @return 实例对象
     */
    @Override
    public ImdbRatings update(ImdbRatings imdbRatings) {
        this.imdbRatingsDao.update(imdbRatings);
        return this.queryById(imdbRatings.getImdbId());
    }

    /**
     * 通过主键删除数据
     *
     * @param imdbId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String imdbId) {
        return this.imdbRatingsDao.deleteById(imdbId) > 0;
    }

    /**
     * 通过豆瓣id查询单条数据
     *
     * @param doubanId 豆瓣id
     * @return 单条数据
     */
    @Override
    public ImdbRatings queryByDoubanId(String doubanId) {
        return this.imdbRatingsDao.queryByDoubanId(doubanId);
    }
}