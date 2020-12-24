package com.pcy.movierecommendation.es;

import com.pcy.movierecommendation.controller.BaseController;
import com.pcy.movierecommendation.core.constants.ErrorMessages;
import com.pcy.movierecommendation.core.model.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * ElasticSearch 控制层
 *
 * @author PengChenyu
 * @since 2020-12-24 21:12:24
 */
@Api(value = "/es", tags = "es")
@RestController
@RequestMapping("api/recommendation/es")
public class ElasticSearchController extends BaseController {

    @Resource
    BaseElasticSearchService baseElasticSearchService;


    /**
     * 判断索引是否存在
     *
     * @param indexName 索引名
     * @return 是否存在的信息
     * @throws IOException BaseController统一处理
     */
    @ApiOperation(value = "判断索引是否存在", notes = "判断索引是否存在（单机）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "indexName", value = "索引名", required = true, dataType = "String")
    })
    @GetMapping("/index/{indexName}")
    public ApiResponse isExistsIndex(@PathVariable("indexName") String indexName) throws IOException {
        boolean isExistsIndex = baseElasticSearchService.isExistsIndex(indexName);
        if (!isExistsIndex) {
            return ApiResponse.failed(ErrorMessages.ELASTICSEARCH_INDEX_NULL);
        }
        return ApiResponse.success(ErrorMessages.ELASTICSEARCH_SEARCH_SUCCESS);
    }

    /**
     * 创建索引
     *
     * @param indexName 索引名
     * @return 是否创建成功
     * @throws IOException BaseController统一处理
     */
    @ApiOperation(value = "创建索引", notes = "创建索引（单机）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "indexName", value = "索引名", required = true, dataType = "String")
    })
    @PostMapping("/index/{indexName}")
    public ApiResponse createIndex(@PathVariable("indexName") String indexName) throws IOException {
        Boolean isSuccess = baseElasticSearchService.createIndex(indexName);
        if (!isSuccess) {
            return ApiResponse.failed(ErrorMessages.ELASTICSEARCH_INDEX_CREATE_FAIL);
        }
        return ApiResponse.success(ErrorMessages.ELASTICSEARCH_INDEX_CREATE_SUCCESS);
    }

    /**
     * 删除索引
     *
     * @param indexName 索引名
     * @return 是否删除成功
     * @throws IOException BaseController统一处理
     */
    @ApiOperation(value = "删除索引", notes = "删除索引（单机），注意删除索引会将索引中的所有数据一并删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "indexName", value = "索引名", required = true, dataType = "String")
    })
    @DeleteMapping("/index/{indexName}")
    public ApiResponse deleteIndex(@PathVariable("indexName") String indexName) throws IOException {
        Boolean isSuccess = baseElasticSearchService.deleteIndex(indexName);
        if (!isSuccess) {
            return ApiResponse.failed(ErrorMessages.ELASTICSEARCH_INDEX_DELETE_FAIL);
        }
        return ApiResponse.success(ErrorMessages.ELASTICSEARCH_INDEX_DELETE_SUCCESS);
    }
}
