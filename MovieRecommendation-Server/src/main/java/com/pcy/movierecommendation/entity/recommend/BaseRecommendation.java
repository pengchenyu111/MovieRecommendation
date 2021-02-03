package com.pcy.movierecommendation.entity.recommend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * 基础推荐对象
 *
 * @author PengChenyu
 * @since 2021-02-03 16:35:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseRecommendation implements Serializable {

    private static final long serialVersionUID = 521976241521655818L;

    /**
     * id
     */
    @Field("id")
    private Integer id;

    /**
     * 权重
     */
    private Double score;
}
