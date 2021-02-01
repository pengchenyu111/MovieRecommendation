package com.pcy.movierecommendation.service.impl;

import com.pcy.movierecommendation.dao.MovieTagDao;
import com.pcy.movierecommendation.entity.movieTag.MovieTag;
import com.pcy.movierecommendation.service.MovieTagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MovieTag)表服务实现类
 *
 * @author PengChenyu
 * @since 2021-02-01 14:34:51
 */
@Service("movieTagService")
public class MovieTagServiceImpl implements MovieTagService {
    @Resource
    private MovieTagDao movieTagDao;

    /**
     * 通过ID查询单条数据
     *
     * @param tagId 主键
     * @return 实例对象
     */
    @Override
    public MovieTag queryById(Integer tagId) {
        return this.movieTagDao.queryById(tagId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<MovieTag> queryAllByLimit(int offset, int limit) {
        return this.movieTagDao.queryAllByLimit(offset, limit);
    }

    /**
     * 查询所有数据
     * @return 对象列表
     */
    @Override
    public List<MovieTag> queryAllTags() {
        return this.movieTagDao.queryAllTags();
    }


    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    @Override
    public int count() {
        return this.movieTagDao.count();
    }


    /**
     * 新增数据
     *
     * @param movieTag 实例对象
     * @return 实例对象
     */
    @Override
    public MovieTag insert(MovieTag movieTag) {
        this.movieTagDao.insert(movieTag);
        return movieTag;
    }

    /**
     * 修改数据
     *
     * @param movieTag 实例对象
     * @return 实例对象
     */
    @Override
    public MovieTag update(MovieTag movieTag) {
        this.movieTagDao.update(movieTag);
        return this.queryById(movieTag.getTagId());
    }

    /**
     * 通过主键删除数据
     *
     * @param tagId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer tagId) {
        return this.movieTagDao.deleteById(tagId) > 0;
    }
}