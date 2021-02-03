package com.pcy.movierecommendation.controller;

import com.pcy.movierecommendation.core.constants.ErrorMessages;
import com.pcy.movierecommendation.core.model.ApiResponse;
import com.pcy.movierecommendation.entity.movieDetail.MovieDetail;
import com.pcy.movierecommendation.service.RecommendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 推荐接口
 *
 * @author PengChenyu
 * @since 2021-02-02 15:38:59
 */
@RestController
@RequestMapping("api/recommendation")
@Api(value = "/recommendation", tags = "recommendation")
public class RecommendController extends BaseController {

    /**
     * 服务对象
     */
    @Resource
    private RecommendService recommendService;

    /**
     * 历史Top20电影
     *
     * @return Top20数据
     */
    @ApiOperation(value = "历史Top20电影", notes = "历史Top20电影")
    @GetMapping("/historyTop20")
    public ApiResponse<List<MovieDetail>> historyTop20() {
        List<MovieDetail> movieDetails = this.recommendService.historyTop20();
        if (movieDetails == null) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, movieDetails);
    }

    /**
     * 近期Top20电影
     *
     * @return Top20数据
     */
    @ApiOperation(value = "近期Top20电影", notes = "近期Top20电影")
    @GetMapping("/recentlyTop20")
    public ApiResponse<List<MovieDetail>> recentlyTop20() {
        List<MovieDetail> movieDetails = this.recommendService.recentlyTop20();
        if (movieDetails == null) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, movieDetails);
    }

    /**
     * 查询各类别电影的Top10
     *
     * @param map 分类标签
     * @return Top10数据
     */
    @ApiOperation(value = "各类别电影的Top10", notes = "各类别电影的Top10")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "map", value = "分类标签", required = true, dataType = "Map")
    })
    @PostMapping("/genreTop10")
    public ApiResponse<List<MovieDetail>> genreTop10(@RequestBody Map<String, String> map) {
        String genre = map.get("genre");
        List<MovieDetail> movieDetails = this.recommendService.genreTop10(genre);
        if (movieDetails == null) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, movieDetails);
    }
}
