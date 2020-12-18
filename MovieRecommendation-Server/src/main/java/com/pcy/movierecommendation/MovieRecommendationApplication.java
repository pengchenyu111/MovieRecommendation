package com.pcy.movierecommendation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动入口
 *
 * @author PengChenyu
 * @since 2020-12-18 17:42:00
 */
@SpringBootApplication
@MapperScan("com.pcy.movierecommendation.dao")
public class MovieRecommendationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieRecommendationApplication.class, args);
    }

}
