package com.pcy.movierecommendation.controller;

import com.pcy.movierecommendation.core.constants.ErrorMessages;
import com.pcy.movierecommendation.core.model.ApiResponse;
import com.pcy.movierecommendation.entity.imdbRatings.ImdbRatings;
import com.pcy.movierecommendation.service.ImdbRatingsService;
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
 * (ImdbRatings)表控制层
 *
 * @author PengChenyu
 * @since 2020-12-27 22:28:28
 */
@Api(value = "/imdbRatings", tags = "imdbRatings")
@RestController
@RequestMapping("api/recommendation/imdbRatings")
public class ImdbRatingsController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ImdbRatingsService imdbRatingsService;

    /**
     * 通过主键查询单条数据
     *
     * @param imdbId 主键
     * @return 单条数据
     */
    @ApiOperation(value = "主键查询", notes = "通过主键查询单条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "imdbId", value = "imdbid", required = true, dataType = "String")
    })
    @GetMapping("/{imdbId}")
    public ApiResponse<ImdbRatings> queryByImdbId(@PathVariable("imdbId") String imdbId) {
        ImdbRatings imdbRatings = this.imdbRatingsService.queryById(imdbId);
        if (imdbRatings == null) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, imdbRatings);
    }

    /**
     * 通过豆瓣id查询单条数据
     * 结果没有格式化
     *
     * @param doubanId 豆瓣id
     * @return 单条数据
     */
    @ApiOperation(value = "豆瓣id查询", notes = "通过豆瓣id查询查询单条数据，结果没有格式化")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "doubanId", value = "doubanId", required = true, dataType = "String")
    })
    @GetMapping("/doubanId/{doubanId}")
    public ApiResponse<ImdbRatings> queryByDouban(@PathVariable("doubanId") String doubanId) {
        ImdbRatings imdbRatings = this.imdbRatingsService.queryByDoubanId(doubanId);
        if (imdbRatings == null) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, imdbRatings);
    }


}