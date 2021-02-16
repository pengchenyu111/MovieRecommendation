package com.pcy.movierecommendation.com.pcy.movierecommendation.service;

import com.pcy.movierecommendation.BaseTest;
import com.pcy.movierecommendation.entity.movieDetail.MovieDetail;
import com.pcy.movierecommendation.service.RecommendService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author PengChenyu
 * @since 2021-02-03 20:21:26
 */
@SpringBootTest
public class RecommendServiceTest extends BaseTest {

    @Resource
    RecommendService recommendService;

    @Test
    void t1() {
        List<String> genres = new ArrayList<>();
        genres.add("剧情");
        genres.add("爱情");
        genres.add("历史");
        List<MovieDetail> res1 = recommendService.genreCompositeTop10(genres);
        print(res1.size());
        print(res1.toString());

        List<String> genres1 = new ArrayList<>();
        genres1.add("同性");
        genres1.add("战争");
        List<MovieDetail> res2 = recommendService.genreCompositeTop10(genres1);
        print(res2.size());
        print(res2.toString());
    }

}
