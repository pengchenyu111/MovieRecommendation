package com.pcy.movierecommendation.controller;

import com.pcy.movierecommendation.core.constants.ErrorMessages;
import com.pcy.movierecommendation.core.model.ApiResponse;
import com.pcy.movierecommendation.entity.movieReviews.MovieUserRatings;
import com.pcy.movierecommendation.service.MovieUserRatingsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.service.ApiInfo;

import javax.annotation.Resource;

/**
 * (MovieUserRatings)表控制层
 *
 * @author PengChenyu
 * @since 2020-12-30 16:25:25
 */
@Api(value = "/movieUserRatings", tags = "movieUserRatings")
@RestController
@RequestMapping("api/recommendation/movieUserRatings")
public class MovieUserRatingsController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private MovieUserRatingsService movieUserRatingsService;

    /**
     * 通过主键查询单条数据
     *
     * @param reviewId 主键
     * @return 单条数据
     */
    @ApiOperation(value = "主键查询", notes = "通过主键查询单条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "reviewId", value = "用户评论id", required = true, dataType = "String")
    })
    @GetMapping("/{reviewId}")
    public ApiResponse<MovieUserRatings> selectOne(@PathVariable("reviewId") String reviewId) {
        MovieUserRatings movieUserRatings = this.movieUserRatingsService.queryById(reviewId);
        if (movieUserRatings == null) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, movieUserRatings);
    }

}