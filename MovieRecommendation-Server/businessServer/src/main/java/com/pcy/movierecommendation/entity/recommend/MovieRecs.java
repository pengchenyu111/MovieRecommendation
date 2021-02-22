package com.pcy.movierecommendation.entity.recommend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

/**
 * @author PengChenyu
 * @since 2021-02-22 16:01:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieRecs implements Serializable {

    private static final long serialVersionUID = -346097265116999744L;

    /**
     * 豆瓣id
     */
    @Field("douban_id")
    private Integer doubanId;

    /**
     * Top列表
     */
    @Field("recs")
    private List<BaseRecommendation> recommendations;
}
