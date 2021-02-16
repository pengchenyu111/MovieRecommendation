package com.pcy.movierecommendation.controller;

import com.pcy.movierecommendation.core.constants.ErrorMessages;
import com.pcy.movierecommendation.core.model.ApiResponse;
import com.pcy.movierecommendation.entity.movieTag.MovieTag;
import com.pcy.movierecommendation.service.MovieTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MovieTag)表控制层
 *
 * @author PengChenyu
 * @since 2021-02-01 14:34:51
 */
@RestController
@RequestMapping("api/recommendation/movieTag")
@Api(value = "/movieTag", tags = "movieTag")
public class MovieTagController {
    /**
     * 服务对象
     */
    @Resource
    private MovieTagService movieTagService;

    /**
     * 查询所有电影标签数据
     *
     * @return 所有电影标签数据
     */
    @ApiOperation(value = "查询所有", notes = "查询所有电影标签数据")
    @GetMapping()
    public ApiResponse<List<MovieTag>> queryAllTags() {
        List<MovieTag> movieTags = this.movieTagService.queryAllTags();
        if (CollectionUtils.isEmpty(movieTags)) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, movieTags);
    }

}