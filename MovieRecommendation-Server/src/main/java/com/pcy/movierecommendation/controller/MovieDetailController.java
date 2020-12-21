package com.pcy.movierecommendation.controller;

import com.pcy.movierecommendation.entity.movieDetail.MovieDetail;
import com.pcy.movierecommendation.service.MovieDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (MovieDetail)表控制层
 *
 * @author PengChenyu
 * @since 2020-12-21 21:41:53
 */
@Api(value = "/movieDetail", tags = "movieDetail")
@RestController
@RequestMapping("api/recommendation/movieDetail")
public class MovieDetailController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private MovieDetailService movieDetailService;

    /**
     * 通过主键查询单条数据
     *
     * @param doubanId 主键
     * @return 单条数据
     */
    @ApiOperation(value = "主键查询", notes = "通过主键查询单条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "doubanId", value = "豆瓣id", required = true, dataType = "Integer")
    })
    @GetMapping("/{doubanId}")
    public MovieDetail selectOne(@PathVariable("doubanId") Integer doubanId) {
        return this.movieDetailService.queryById(doubanId);
    }

}