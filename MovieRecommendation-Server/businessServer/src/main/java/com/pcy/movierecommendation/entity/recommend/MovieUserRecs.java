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
public class MovieUserRecs implements Serializable {

    private static final long serialVersionUID = 8914516701263893683L;

    /**
     * 用户id
     */
    @Field("user_id")
    private Integer userId;

    /**
     * Top列表
     */
    @Field("recs")
    private List<BaseRecommendation> recommendations;
}
