package com.pcy.movierecommendation.controller;

import com.github.pagehelper.PageInfo;
import com.pcy.movierecommendation.core.constants.ErrorMessages;
import com.pcy.movierecommendation.core.model.ApiResponse;
import com.pcy.movierecommendation.entity.movieDetail.MovieDetail;
import com.pcy.movierecommendation.service.MovieDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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
    public ApiResponse<MovieDetail> selectOne(@PathVariable("doubanId") Integer doubanId) {
        MovieDetail movieDetail = this.movieDetailService.queryById(doubanId);
        if (movieDetail == null) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, movieDetail);
    }


    /**
     * 分页查询
     *
     * @param pageNum     当前页
     * @param pageSize    每页多少数据
     * @param movieDetail 查询条件
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "当前页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页的数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "movieDetail", value = "查询条件", required = true, dataType = "MovieDetail")
    })
    @PostMapping("/page/{pageNum}/{pageSize}")
    public ApiResponse<PageInfo<MovieDetail>> queryPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, @RequestBody MovieDetail movieDetail) {
        PageInfo<MovieDetail> movieDetailPageInfo = movieDetailService.queryPage(pageNum, pageSize, movieDetail);
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, movieDetailPageInfo);
    }

}