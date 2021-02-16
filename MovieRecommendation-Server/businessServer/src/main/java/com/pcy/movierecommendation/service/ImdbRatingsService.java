package com.pcy.movierecommendation.service;

import com.pcy.movierecommendation.entity.imdbRatings.FormatImdbRatings;
import com.pcy.movierecommendation.entity.imdbRatings.ImdbRatings;

import java.util.List;

/**
 * (ImdbRatings)表服务接口
 *
 * @author PengChenyu
 * @since 2020-12-27 22:28:28
 */
public interface ImdbRatingsService {

    /**
     * 通过ID查询单条数据
     *
     * @param imdbId 主键
     * @return 实例对象
     */
    ImdbRatings queryById(String imdbId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ImdbRatings> queryAllByLimit(int offset, int limit);


    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    int count();


    /**
     * 新增数据
     *
     * @param imdbRatings 实例对象
     * @return 实例对象
     */
    ImdbRatings insert(ImdbRatings imdbRatings);

    /**
     * 修改数据
     *
     * @param imdbRatings 实例对象
     * @return 实例对象
     */
    ImdbRatings update(ImdbRatings imdbRatings);

    /**
     * 通过主键删除数据
     *
     * @param imdbId 主键
     * @return 是否成功
     */
    boolean deleteById(String imdbId);

    /**
     * 通过豆瓣id查询单条数据
     * 结果没有格式化
     *
     * @param doubanId 豆瓣id
     * @return 单条数据
     */
    ImdbRatings queryByDoubanId(String doubanId);

    /**
     * 通过豆瓣id查询单条数据
     * 格式化
     *
     * @param doubanId 豆瓣id
     * @return 单条数据
     */
    FormatImdbRatings queryByDoubanIdFormat(String doubanId);
}