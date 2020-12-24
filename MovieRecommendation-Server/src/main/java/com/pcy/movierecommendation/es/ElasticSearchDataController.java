package com.pcy.movierecommendation.es;

import com.pcy.movierecommendation.core.constants.ErrorMessages;
import com.pcy.movierecommendation.core.model.ApiResponse;
import com.pcy.movierecommendation.entity.movieDetail.MovieDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author PengChenyu
 * @since 2020-12-24 23:42:30
 */
@Api(value = "/es/data", tags = "es-data")
@RestController
@RequestMapping("api/recommendation/es/data")
public class ElasticSearchDataController {

    @Resource
    BaseElasticSearchService baseElasticSearchService;

    /**
     * 插入MovieDetail数据（所有字段）
     *
     * @param indexName   索引名
     * @param movieDetail 数据
     * @throws IOException BaseController统一处理
     */
    @ApiOperation(value = "插入电影数据", notes = "插入MovieDetail数据（所有字段）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "indexName", value = "索引名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "body", name = "movieDetail", value = "电影详情数据", required = true, dataType = "MovieDetail")
    })
    @PostMapping("/{indexName}")
    public ApiResponse insertOrUpdateDocOne(@PathVariable("indexName") String indexName, @RequestBody MovieDetail movieDetail) throws IOException {
        Boolean isSuccess = baseElasticSearchService.insertDocOne(indexName, movieDetail);
        if (!isSuccess) {
            return ApiResponse.failed(ErrorMessages.ELASTICSEARCH_DATA_INSERT_FAIL);
        }
        return ApiResponse.success(ErrorMessages.ELASTICSEARCH_DATA_INSERT_SUCCESS);
    }
}
