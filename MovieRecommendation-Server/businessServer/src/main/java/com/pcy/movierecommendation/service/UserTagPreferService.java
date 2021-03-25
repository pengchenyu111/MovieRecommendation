package com.pcy.movierecommendation.service;

import com.pcy.movierecommendation.entity.movieTag.MovieTag;
import com.pcy.movierecommendation.entity.movieTag.UserTagPrefer;

import java.util.List;

/**
 * (UserTagPrefer)表服务接口
 *
 * @author PengChenyu
 * @since 2021-02-03 21:32:33
 */
public interface UserTagPreferService {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    UserTagPrefer queryById(Integer userId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<UserTagPrefer> queryAllByLimit(int offset, int limit);


    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    int count();


    /**
     * 新增数据
     *
     * @param userTagPrefer 实例对象
     * @return 实例对象
     */
    UserTagPrefer insert(UserTagPrefer userTagPrefer);

    /**
     * 修改数据
     *
     * @param userTagPrefer 实例对象
     * @return 实例对象
     */
    UserTagPrefer update(UserTagPrefer userTagPrefer);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer userId);

    /**
     * 通过主键查询详细用户喜好标签数据
     *
     * @param userId 主键
     * @return 标签列表数据
     */
    List<MovieTag> queryFullInfoById(Integer userId);
}