package com.pcy.movierecommendation.entity.movieDetail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 电影详情标签查询请求对象
 *
 * @author PengChenyu
 * @since 2020-12-27 16:37:38
 */
@ApiModel(value = "MovieDetailSearchRequest", description = "电影详情标签查询请求对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDetailSearchRequest {

    @ApiModelProperty("第几页")
    private Integer pageNum;

    @ApiModelProperty("每页大小")
    private Integer pageSize;

    @ApiModelProperty("类型")
    private String types;

    @ApiModelProperty("制片国家/地区")
    private String productionCountryArea;

    @ApiModelProperty("语言")
    private String language;

    @ApiModelProperty("评分上界")
    private Double ratingScoreUpperBound;

    @ApiModelProperty("评分下界")
    private Double ratingScoreLowerBound;
}
