package com.pcy.movierecommendation.service.movieUser;

import com.pcy.movierecommendation.entity.movieUser.MovieUser;

import java.util.List;

/**
 * (MovieUser)表服务接口
 *
 * @author PengChenyu
 * @since 2020-12-18 17:42:00
 */
public interface MovieUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    MovieUser queryById(Integer userId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MovieUser> queryAllByLimit(int offset, int limit);


    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    int count();


    /**
     * 新增数据
     *
     * @param movieUser 实例对象
     * @return 实例对象
     */
    MovieUser insert(MovieUser movieUser);

    /**
     * 修改数据
     *
     * @param movieUser 实例对象
     * @return 实例对象
     */
    MovieUser update(MovieUser movieUser);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer userId);

}