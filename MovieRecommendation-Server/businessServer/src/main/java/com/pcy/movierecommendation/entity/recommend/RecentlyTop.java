package com.pcy.movierecommendation.entity.recommend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * 近期热门TOP电影
 * 对应MongoDB的 recently_top 表
 *
 * @author PengChenyu
 * @since 2021-02-02 22:35:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecentlyTop implements Serializable {

    private static final long serialVersionUID = -5728553260882290032L;

    /**
     * 豆瓣id
     */
    @Field("douban_id")
    private Integer doubanId;

    /**
     * 该月评论数
     */
    private Integer count;

    /**
     * 月份，yyyyMM
     */
    private String yearmonth;
}
