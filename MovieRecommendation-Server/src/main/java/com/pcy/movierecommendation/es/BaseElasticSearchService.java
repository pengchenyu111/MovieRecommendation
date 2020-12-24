package com.pcy.movierecommendation.es;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

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

}
