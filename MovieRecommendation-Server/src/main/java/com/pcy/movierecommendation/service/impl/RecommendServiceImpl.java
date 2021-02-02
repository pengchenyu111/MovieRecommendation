package com.pcy.movierecommendation.service.impl;

import com.pcy.movierecommendation.core.constants.DBConstant;
import com.pcy.movierecommendation.entity.movieDetail.MovieDetail;
import com.pcy.movierecommendation.service.RecommendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 推荐服务
 *
 * @author PengChenyu
 * @since 2021-02-02 15:16:27
 */
@Service("recommendService")
public class RecommendServiceImpl implements RecommendService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private MongoTemplate mongoTemplate;


    @Override
    public List<MovieDetail> historyTop20() {
        List<MovieDetail> movieDetails = mongoTemplate.findAll(MovieDetail.class, DBConstant.MONGO_COLLECTION_HISTORY_TOP_20);
        logger.info("【MongoDB查询-历史热门Top20】:" + movieDetails.toString());
        return movieDetails;
    }
}
