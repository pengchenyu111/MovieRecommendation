package com.pcy.movierecommendation.controller;

import com.github.pagehelper.PageInfo;
import com.pcy.movierecommendation.core.constants.ErrorMessages;
import com.pcy.movierecommendation.core.model.ApiResponse;
import com.pcy.movierecommendation.entity.movieReviews.MovieReviews;
import com.pcy.movierecommendation.service.MovieReviewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MovieReviews)表控制层
 *
 * @author PengChenyu
 * @since 2020-12-29 20:08:19
 */
@Api(value = "/movieReviews", tags = "movieReviews")
@RestController
@RequestMapping("api/recommendation/movieReviews")
public class MovieReviewsController {
    /**
     * 服务对象
     */
    @Resource
    private MovieReviewsService movieReviewsService;

    /**
     * 通过主键查询单条数据
     *
     * @param reviewId 主键
     * @return 单条数据
     */
    @ApiOperation(value = "主键查询", notes = "通过主键查询单条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "reviewId", value = "评论id", required = true, dataType = "String")
    })
    @GetMapping("/{reviewId}")
    public ApiResponse<MovieReviews> selectOne(@PathVariable("reviewId") String reviewId) {
        MovieReviews movieReviews = this.movieReviewsService.queryById(reviewId);
        if (movieReviews == null) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, movieReviews);
    }

    /**
     * 根据doubanId分页查询
     *
     * @param doubanId 豆瓣id
     * @param pageNum  当前页
     * @param pageSize 每页多少数据
     * @return 分页数据
     */
    @ApiOperation(value = "主键查询", notes = "通过主键查询单条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "doubanId", value = "豆瓣id", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页的数量", required = true, dataType = "Integer")
    })
    @GetMapping("/doubanId/{doubanId}/page/{pageNum}/{pageSize}")
    public ApiResponse<PageInfo<MovieReviews>> queryByDoubanIdPage(@PathVariable("doubanId") Integer doubanId, @PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) {
        PageInfo<MovieReviews> movieReviewsList = this.movieReviewsService.queryByDoubanIdPage(doubanId, pageNum, pageSize);
        if (movieReviewsList.getTotal() == 0L) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, movieReviewsList);
    }

}