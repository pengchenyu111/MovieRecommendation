package com.pcy.movierecommendation.controller.movieUser;

import com.pcy.movierecommendation.entity.movieUser.MovieUser;
import com.pcy.movierecommendation.service.movieUser.MovieUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (MovieUser)表控制层
 *
 * @author PengChenyu
 * @since 2020-12-18 17:42:00
 */
@RestController
@RequestMapping("movieUser")
public class MovieUserController {
    /**
     * 服务对象
     */
    @Resource
    private MovieUserService movieUserService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public MovieUser selectOne(Integer id) {
        return this.movieUserService.queryById(id);
    }

}