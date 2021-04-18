package com.pcy.movierecommendation.controller;

import com.github.pagehelper.PageInfo;
import com.pcy.movierecommendation.core.constants.ErrorMessages;
import com.pcy.movierecommendation.core.model.ApiResponse;
import com.pcy.movierecommendation.entity.movieReviews.MovieReviews;
import com.pcy.movierecommendation.entity.movieReviews.MovieUserRatings;
import com.pcy.movierecommendation.entity.movieReviews.UserReview;
import com.pcy.movierecommendation.service.MovieReviewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation(value = "doubanId分页查询", notes = "doubanId分页查询")
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

    /**
     * 根据userId分页查询用户历史评论
     *
     * @param userId   用户id
     * @param pageNum  当前页
     * @param pageSize 每页多少数据
     * @return 分页数据
     */
    @ApiOperation(value = "用户历史评论", notes = "用户历史评论")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页的数量", required = true, dataType = "Integer")
    })
    @GetMapping("/userHistoryReviews/{userId}/page/{pageNum}/{pageSize}")
    public ApiResponse<PageInfo<UserReview>> userHistoryReviews(@PathVariable("userId") Integer userId, @PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) {
        PageInfo<UserReview> userReviewsList = this.movieReviewsService.userHistoryReviews(userId, pageNum, pageSize);
        if (userReviewsList.getTotal() == 0L) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, userReviewsList);
    }

    /**
     * 用户评论
     *
     * @param movieReviews 评论信息
     * @return 评论信息
     */
    @ApiOperation(value = "用户评论", notes = "用户评论")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "movieReviews", value = "评论信息", required = true, dataType = "MovieReviews")
    })
    @PostMapping
    public ApiResponse<MovieReviews> review(@RequestBody MovieReviews movieReviews) {
        MovieReviews review = this.movieReviewsService.review(movieReviews);
        if (review == null) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.REQUEST_FAIL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, review);
    }

    /**
     * 用户给评论点赞
     *
     * @param reviewId 评论id
     * @return 评论点赞是否成功
     */
    @ApiOperation(value = "用户评论点赞", notes = "用户评论点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "reviewId", value = "评论id", required = true, dataType = "String")
    })
    @GetMapping("/agree/{reviewId}")
    public ApiResponse<MovieReviews> agree(@PathVariable("reviewId") String reviewId) {
        MovieReviews updatedMovieReviews = this.movieReviewsService.agree(reviewId);
        if (updatedMovieReviews == null) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.REQUEST_FAIL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, updatedMovieReviews);
    }

    /**
     * 获取用户最近的K次评分数据
     *
     * @param userId 用户id
     * @param k      数据量
     * @return 数据列表
     */
    @ApiOperation(value = "获取用户最近的K次评分数据", notes = "获取用户最近的K次评分数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "k", value = "评论数据量", required = true, dataType = "Integer")
    })
    @GetMapping("recent/{userId}/{k}")
    public ApiResponse<List<MovieReviews>> kRecentRatings(@PathVariable("userId") Integer userId, @PathVariable("k") Integer k) {
        List<MovieReviews> movieReviewsList = this.movieReviewsService.kRecentRatings(userId, k);
        if (CollectionUtils.isEmpty(movieReviewsList)) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, movieReviewsList);
    }

}