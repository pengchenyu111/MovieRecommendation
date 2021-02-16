package com.pcy.movierecommendation.entity.recommend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 给类别电影Top列表
 *
 * @author PengChenyu
 * @since 2021-02-03 16:49:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreTop implements Serializable {

    private static final long serialVersionUID = 8842440845045170737L;

    /**
     * 类别
     */
    private String genre;

    /**
     * Top列表
     */
    private List<BaseRecommendation> recommendations;
}
