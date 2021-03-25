package com.pcy.movierecommendation.service.impl;

import com.pcy.movierecommendation.dao.MovieTagDao;
import com.pcy.movierecommendation.dao.UserTagPreferDao;
import com.pcy.movierecommendation.entity.movieTag.MovieTag;
import com.pcy.movierecommendation.entity.movieTag.UserTagPrefer;
import com.pcy.movierecommendation.service.UserTagPreferService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (UserTagPrefer)表服务实现类
 *
 * @author PengChenyu
 * @since 2021-02-03 21:32:33
 */
@Service("userTagPreferService")
public class UserTagPreferServiceImpl implements UserTagPreferService {
    @Resource
    private UserTagPreferDao userTagPreferDao;
    @Resource
    MovieTagDao movieTagDao;

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public UserTagPrefer queryById(Integer userId) {
        return this.userTagPreferDao.queryById(userId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<UserTagPrefer> queryAllByLimit(int offset, int limit) {
        return this.userTagPreferDao.queryAllByLimit(offset, limit);
    }


    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    @Override
    public int count() {
        return this.userTagPreferDao.count();
    }


    /**
     * 新增数据
     *
     * @param userTagPrefer 实例对象
     * @return 实例对象
     */
    @Override
    public UserTagPrefer insert(UserTagPrefer userTagPrefer) {
        this.userTagPreferDao.insert(userTagPrefer);
        return userTagPrefer;
    }

    /**
     * 修改数据
     *
     * @param userTagPrefer 实例对象
     * @return 实例对象
     */
    @Override
    public UserTagPrefer update(UserTagPrefer userTagPrefer) {
        this.userTagPreferDao.update(userTagPrefer);
        return this.queryById(userTagPrefer.getUserId());
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer userId) {
        return this.userTagPreferDao.deleteById(userId) > 0;
    }

    /**
     * 通过主键查询详细用户喜好标签数据
     *
     * @param userId 主键
     * @return 标签列表数据
     */
    @Override
    public List<MovieTag> queryFullInfoById(Integer userId) {
        UserTagPrefer userTagPrefer = this.userTagPreferDao.queryById(userId);
        List<Integer> idList = Arrays.stream(userTagPrefer.getTagList().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        return this.movieTagDao.queryByIdList(idList);
    }
}