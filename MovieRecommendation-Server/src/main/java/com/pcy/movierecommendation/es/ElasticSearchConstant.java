package com.pcy.movierecommendation.es;

/**
 * @author PengChenyu
 * @since 2020-12-24 22:13:06
 */
public class ElasticSearchConstant {

    /**
     * 分片数，单节点下设置意义不大
     */
    public static final int NUMBER_OF_SHARDS = 3;

    /**
     * 副本数，单节点下设置意义不大
     */
    public static final int NUMBER_OF_REPLICAS = 2;
}
