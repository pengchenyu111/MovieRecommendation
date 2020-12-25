package com.pcy.movierecommendation.es;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ES展示类
 *
 * @author PengChenyu
 * @since 2020-12-25 16:10:48
 */
@ApiModel(value = "ElasticSearchVo", description = "ES展示对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElasticSearchVo<T> {

    @ApiModelProperty("结果总数")
    private Long total;

    @ApiModelProperty("结果列表")
    private List<T> resultList;
}
