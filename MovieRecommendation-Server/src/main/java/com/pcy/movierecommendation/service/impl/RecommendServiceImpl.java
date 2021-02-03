package com.pcy.movierecommendation.service.impl;

import com.pcy.movierecommendation.core.constants.DBConstant;
import com.pcy.movierecommendation.entity.movieDetail.MovieDetail;
import com.pcy.movierecommendation.entity.recommend.BaseRecommendation;
import com.pcy.movierecommendation.entity.recommend.GenreTop;
import com.pcy.movierecommendation.entity.recommend.RecentlyTop;
import com.pcy.movierecommendation.service.MovieDetailService;
import com.pcy.movierecommendation.service.RecommendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    @Resource
    private MovieDetailService movieDetailService;

    @Override
    public List<MovieDetail> historyTop20() {
        List<MovieDetail> movieDetails = mongoTemplate.findAll(MovieDetail.class, DBConstant.MONGO_COLLECTION_HISTORY_TOP_20);
        logger.info("【MongoDB查询-历史热门Top20】:" + movieDetails.toString());
        return movieDetails;
    }

    @Override
    public List<MovieDetail> recentlyTop20() {
        // 从MongoDB中查询出近期的前20条电影
        Query query = new Query(new Criteria()).limit(20);
        List<RecentlyTop> recentlyTops = mongoTemplate.find(query, RecentlyTop.class, DBConstant.MONGO_COLLECTION_RECENTLY_TOP);
        logger.info("【MongoDB查询-近期热门Top20】:" + recentlyTops.toString());
        // 提取出其douban_id
        List<Integer> doubanIdList = recentlyTops.stream().map(RecentlyTop::getDoubanId).collect(Collectors.toList());
        // 去数据库查询得出最终详细结果
        List<MovieDetail> movieDetails = movieDetailService.queryByIdList(doubanIdList);
        return movieDetails;
    }

    @Override
    public List<MovieDetail> genreTop10(String genre) {
        // 从MongoDB中找出该类别
        Query query = Query.query(Criteria.where("genre").is(genre));
        GenreTop genreTop = mongoTemplate.findOne(query, GenreTop.class, DBConstant.MONGO_COLLECTION_GENRE_TOP);
        // 判空
        if (genreTop == null) {
            return new ArrayList<MovieDetail>();
        }
        logger.info("【MongoDB查询-类别Top10】:" + genreTop.toString());
        // 取出doubanId
        List<Integer> doubanIdList = genreTop.getRecommendations().stream().map(BaseRecommendation::getId).collect(Collectors.toList());
        return movieDetailService.queryByIdList(doubanIdList);
    }

    @Override
    public List<MovieDetail> genreCompositeTop10(List<String> genreList) {
        // 从MongoDB中找出该类别
        Query query = Query.query(Criteria.where("genre").in(genreList));
        List<GenreTop> genreTopList = mongoTemplate.find(query, GenreTop.class, DBConstant.MONGO_COLLECTION_GENRE_TOP);
        logger.info("【MongoDB查询-多类别Top10】:" + genreTopList.toString());
        // 合并内部list，去重，按照score排序,取前10个
        List<BaseRecommendation> baseRecommendationList = genreTopList.stream()
                .map(GenreTop::getRecommendations)
                .flatMap(Collection::stream)
                .distinct()
                .sorted(Comparator.comparing(BaseRecommendation::getScore).reversed())
                .collect(Collectors.toList());
        baseRecommendationList = baseRecommendationList.size() <= 10 ? baseRecommendationList : baseRecommendationList.subList(0, 10);
        List<Integer> doubanIdList = baseRecommendationList.stream().map(BaseRecommendation::getId).collect(Collectors.toList());
        // 根据id列表查询
        return movieDetailService.queryByIdList(doubanIdList);
    }
}
