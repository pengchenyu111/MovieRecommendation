package com.pcy.movierecommendation.service;

import com.pcy.movierecommendation.entity.movieTag.MovieTag;

import java.util.List;

/**
 * (MovieTag)表服务接口
 *
 * @author PengChenyu
 * @since 2021-02-01 14:34:51
 */
public interface MovieTagService {

    /**
     * 通过ID查询单条数据
     *
     * @param tagId 主键
     * @return 实例对象
     */
    MovieTag queryById(Integer tagId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MovieTag> queryAllByLimit(int offset, int limit);

    /**
     * 查询所有数据
     *
     * @return 对象列表
     */
    List<MovieTag> queryAllTags();

    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    int count();


    /**
     * 新增数据
     *
     * @param movieTag 实例对象
     * @return 实例对象
     */
    MovieTag insert(MovieTag movieTag);

    /**
     * 修改数据
     *
     * @param movieTag 实例对象
     * @return 实例对象
     */
    MovieTag update(MovieTag movieTag);

    /**
     * 通过主键删除数据
     *
     * @param tagId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer tagId);

}