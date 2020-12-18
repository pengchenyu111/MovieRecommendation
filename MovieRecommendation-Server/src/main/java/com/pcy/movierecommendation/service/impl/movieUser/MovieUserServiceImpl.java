package com.pcy.movierecommendation.service.impl.movieUser;

import com.pcy.movierecommendation.dao.movieUser.MovieUserDao;
import com.pcy.movierecommendation.entity.movieUser.MovieUser;
import com.pcy.movierecommendation.service.movieUser.MovieUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MovieUser)表服务实现类
 *
 * @author PengChenyu
 * @since 2020-12-18 17:42:00
 */
@Service("movieUserService")
public class MovieUserServiceImpl implements MovieUserService {
    @Resource
    private MovieUserDao movieUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public MovieUser queryById(Integer userId) {
        return this.movieUserDao.queryById(userId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<MovieUser> queryAllByLimit(int offset, int limit) {
        return this.movieUserDao.queryAllByLimit(offset, limit);
    }


    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    @Override
    public int count() {
        return this.movieUserDao.count();
    }


    /**
     * 新增数据
     *
     * @param movieUser 实例对象
     * @return 实例对象
     */
    @Override
    public MovieUser insert(MovieUser movieUser) {
        this.movieUserDao.insert(movieUser);
        return movieUser;
    }

    /**
     * 修改数据
     *
     * @param movieUser 实例对象
     * @return 实例对象
     */
    @Override
    public MovieUser update(MovieUser movieUser) {
        this.movieUserDao.update(movieUser);
        return this.queryById(movieUser.getUserId());
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer userId) {
        return this.movieUserDao.deleteById(userId) > 0;
    }
}