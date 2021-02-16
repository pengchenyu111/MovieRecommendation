package com.pcy.movierecommendation.es;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.ArrayUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * ElasticSearch核心业务逻辑
 * API官方文档：https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.x/java-rest-high.html
 *
 * @author PengChenyu
 * @since 2020-12-24 21:01:41
 */
@Component
public class BaseElasticSearchService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final float FILTER_RATE = 0.95f;

    @Resource
    RestHighLevelClient restHighLevelClient;

    /**
     * 判断某个index是否存在
     *
     * @param indexName index名
     * @return boolean
     */
    public boolean isExistsIndex(String indexName) throws IOException {
        GetIndexRequest request = new GetIndexRequest(indexName);
        return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 添加一个索引
     *
     * @param indexName 索引名
     * @return 是否添加成功
     */
    public Boolean createIndex(String indexName) throws IOException {
        if (this.isExistsIndex(indexName)) {
            logger.info("索引已存在");
            return false;
        }
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        // 设置分片数和副本数
        request.settings(Settings.builder()
                .put("index.number_of_shards", ElasticSearchConstant.NUMBER_OF_SHARDS)
                .put("index.number_of_replicas", ElasticSearchConstant.NUMBER_OF_REPLICAS)
        );
        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    /**
     * 删除索引
     *
     * @param indexName 索引名
     * @return 是否删除成功
     */
    public Boolean deleteIndex(String indexName) throws IOException {
        if (!this.isExistsIndex(indexName)) {
            logger.info("索引不存在，删除失败");
            return false;
        }
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        AcknowledgedResponse response = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    /**
     * 插入或更新一整个数据（所有字段）
     *
     * @param indexName 索引名
     * @param data      数据
     * @return 是否成功
     */
    public <T> Boolean insertDocOne(String indexName, T data) throws IOException {
        IndexRequest request = new IndexRequest(indexName);
        request.timeout(TimeValue.timeValueSeconds(1));
        request.source(JSON.toJSONString(data), XContentType.JSON);
        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        return "CREATED".equals(response.status().toString());
    }

    /**
     * 批量插入数据
     *
     * @param indexName 索引名
     * @param dataList  数据
     * @return 是否成功
     */
    public <T> Boolean insertDocBatch(String indexName, List<T> dataList) throws IOException {
        logger.info("=====>开始批量插入数据");
        BulkRequest bulkRequest = new BulkRequest();
        for (T data : dataList) {
            bulkRequest.add(new IndexRequest(indexName)
                    .source(JSON.toJSONString(data), XContentType.JSON));
        }
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        logger.info("=====>批量插入数据结束");
        return bulkResponse.hasFailures();
    }

    /**
     * 查询
     * SearchSourceBuilder中的具体查询参数由具体业务中的逻辑决定
     *
     * @param indexName 索引名
     * @param builder   查询参数
     * @param c         结果类对象   调用者通过 Xxx.class 获取
     * @return 对象列表
     */
    public <T> ElasticSearchVo<T> search(String indexName, SearchSourceBuilder builder, Class<?> c) throws IOException {
        // 设置查询参数
        SearchRequest request = new SearchRequest(indexName);
        request.source(builder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        // 写入结果
        ElasticSearchVo<T> elasticSearchVo = new ElasticSearchVo<>();
        elasticSearchVo.setTotal(response.getHits().getTotalHits().value);
        elasticSearchVo.setMaxScore(response.getHits().getMaxScore());
        SearchHit[] hits = response.getHits().getHits();
        List<T> result = new ArrayList<>(hits.length);
        for (SearchHit hit : hits) {
            result.add(JSON.parseObject(hit.getSourceAsString(), (Type) c));
        }
        elasticSearchVo.setResultList(result);
        return elasticSearchVo;
    }


    /**
     * 滚动查询
     * 处理返回数据较多而不想分页的情况
     *
     * @param indexName 索引名
     * @param builder   查询参数
     * @param c         结果类对象   调用者通过 Xxx.class 获取
     * @return 对象列表
     * @apiNote 经测试，这种方法效率很低、耗时高，不到万不得已别使用这个接口
     */
    public <T> ElasticSearchVo<T> scrollSearch(String indexName, SearchSourceBuilder builder, Class<?> c) throws IOException {
        // 初始化Scroll
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        // 设置查询条件
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.scroll(scroll);
        searchRequest.source(builder);
        // 发起请求获取结果
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        String scrollId = searchResponse.getScrollId();
        logger.info("滚动查询id======>" + scrollId);
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        SearchHit[] resultList = new SearchHit[0];
        resultList = (SearchHit[]) ArrayUtils.addAll(searchHits, resultList);
        // 通过循环调用Search Scroll API检索所有搜索结果，直到没有文档返回
        while (searchHits != null && searchHits.length > 0) {
            // 创建一个新的SearchScrollRequest保存最后返回的滚动标识符和滚动间隔
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll);
            searchResponse = restHighLevelClient.scroll(scrollRequest, RequestOptions.DEFAULT);
            scrollId = searchResponse.getScrollId();
            searchHits = searchResponse.getHits().getHits();
            resultList = (SearchHit[]) ArrayUtils.addAll(searchHits, resultList);
        }
        // 写入结果
        ElasticSearchVo<T> elasticSearchVo = new ElasticSearchVo<>();
        elasticSearchVo.setTotal(Long.parseLong(String.valueOf(resultList.length)));
        elasticSearchVo.setMaxScore(resultList[0].getScore());
        List<T> result = new ArrayList<>(resultList.length);
        for (SearchHit hit : resultList) {
            result.add(JSON.parseObject(hit.getSourceAsString(), (Type) c));
        }
        elasticSearchVo.setResultList(result);
        // 滚动完成后，清除滚动上下文
        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);
        ClearScrollResponse clearScrollResponse = restHighLevelClient.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        if (!clearScrollResponse.isSucceeded()) {
            logger.info("上下文清除失败");
        }
        return elasticSearchVo;
    }

}
