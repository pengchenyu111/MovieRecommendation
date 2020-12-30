package com.pcy.movierecommendation.entity.movieReviews;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (MovieUserRatings)实体类
 *
 * @author PengChenyu
 * @since 2020-12-30 16:25:23
 */
@ApiModel(value = "MovieUserRatings", description = "用户评论类简化对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieUserRatings implements Serializable {

    private static final long serialVersionUID = -39156122020235906L;

    @ApiModelProperty("评论id")
    private String reviewId;

    @ApiModelProperty("电影豆瓣id")
    private Integer doubanId;

    @ApiModelProperty("用户唯一名字标志，短评上没有id，以此做唯一标识")
    private String userUniqueName;

    @ApiModelProperty("用户评分")
    private Double userMovieRating;


}