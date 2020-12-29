package com.pcy.movierecommendation.service.impl;

import com.pcy.movierecommendation.core.utils.ObjectUtil;
import com.pcy.movierecommendation.dao.ImdbRatingsDao;
import com.pcy.movierecommendation.entity.imdbRatings.*;
import com.pcy.movierecommendation.service.ImdbRatingsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (ImdbRatings)表服务实现类
 *
 * @author PengChenyu
 * @since 2020-12-27 22:28:28
 */
@Service("imdbRatingsService")
public class ImdbRatingsServiceImpl implements ImdbRatingsService {
    @Resource
    private ImdbRatingsDao imdbRatingsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param imdbId 主键
     * @return 实例对象
     */
    @Override
    public ImdbRatings queryById(String imdbId) {
        return this.imdbRatingsDao.queryById(imdbId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ImdbRatings> queryAllByLimit(int offset, int limit) {
        return this.imdbRatingsDao.queryAllByLimit(offset, limit);
    }


    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    @Override
    public int count() {
        return this.imdbRatingsDao.count();
    }


    /**
     * 新增数据
     *
     * @param imdbRatings 实例对象
     * @return 实例对象
     */
    @Override
    public ImdbRatings insert(ImdbRatings imdbRatings) {
        this.imdbRatingsDao.insert(imdbRatings);
        return imdbRatings;
    }

    /**
     * 修改数据
     *
     * @param imdbRatings 实例对象
     * @return 实例对象
     */
    @Override
    public ImdbRatings update(ImdbRatings imdbRatings) {
        this.imdbRatingsDao.update(imdbRatings);
        return this.queryById(imdbRatings.getImdbId());
    }

    /**
     * 通过主键删除数据
     *
     * @param imdbId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String imdbId) {
        return this.imdbRatingsDao.deleteById(imdbId) > 0;
    }

    /**
     * 通过豆瓣id查询单条数据
     *
     * @param doubanId 豆瓣id
     * @return 单条数据
     */
    @Override
    public ImdbRatings queryByDoubanId(String doubanId) {
        return this.imdbRatingsDao.queryByDoubanId(doubanId);
    }

    /**
     * 通过豆瓣id查询单条数据
     *
     * @param doubanId 豆瓣id
     * @return
     */
    @Override
    public FormatImdbRatings queryByDoubanIdFormat(String doubanId) {
        ImdbRatings imdbRatings = this.imdbRatingsDao.queryByDoubanId(doubanId);
        if (imdbRatings == null) {
            return null;
        }
        return imdbRatingsFormater(imdbRatings);
    }

    private FormatImdbRatings imdbRatingsFormater(ImdbRatings imdbRatings) {
        FormatImdbRatings formatImdbRatings = new FormatImdbRatings();
        formatImdbRatings.setImdbId(imdbRatings.getImdbId());
        formatImdbRatings.setDoubanId(imdbRatings.getDoubanId());
        formatImdbRatings.setImdbRating(imdbRatings.getImdbRating());
        formatImdbRatings.setAllRangeRatingScoreWeightVote(allRangeRatingScoreWeightVoteHandler(imdbRatings));
        formatImdbRatings.setAllAgeRatingVote(allAgeRatingVoteHandler(imdbRatings));
        formatImdbRatings.setAllSexRatingVote(allSexRatingVoteHandler(imdbRatings));
        return formatImdbRatings;
    }

    private AllRangeRatingScoreWeightVote allRangeRatingScoreWeightVoteHandler(ImdbRatings imdbRatings) {
        AllRangeRatingScoreWeightVote allRange = new AllRangeRatingScoreWeightVote();
        List<String> ratingScoreList = ObjectUtil.transforString2List(imdbRatings.getRatingScores(), "\\|");
        List<String> ratingScoreWeightList = ObjectUtil.transforString2List(imdbRatings.getRatingScoresWeights(), "\\|");
        List<String> ratingVoteList = ObjectUtil.transforString2List(imdbRatings.getRatingScoresVotes(), "\\|");
        List<RatingScoreWeightVote> ratingScoreWeightVotesList = new ArrayList<>(ratingScoreList.size());
        for (int i = 0; i < ratingScoreList.size(); i++) {
            RatingScoreWeightVote ratingScoreWeightVote = new RatingScoreWeightVote();
            ratingScoreWeightVote.setRatingScores(Integer.parseInt(ratingScoreList.get(i)));
            ratingScoreWeightVote.setRatingScoresWeights(ratingScoreWeightList.get(i));
            ratingScoreWeightVote.setRatingScoresVotes(Integer.parseInt(ratingVoteList.get(i)));
            ratingScoreWeightVotesList.add(ratingScoreWeightVote);
        }
        allRange.setRatingScoreWeightVote10(ratingScoreWeightVotesList.get(0));
        allRange.setRatingScoreWeightVote9(ratingScoreWeightVotesList.get(1));
        allRange.setRatingScoreWeightVote8(ratingScoreWeightVotesList.get(2));
        allRange.setRatingScoreWeightVote7(ratingScoreWeightVotesList.get(3));
        allRange.setRatingScoreWeightVote6(ratingScoreWeightVotesList.get(4));
        allRange.setRatingScoreWeightVote5(ratingScoreWeightVotesList.get(5));
        allRange.setRatingScoreWeightVote4(ratingScoreWeightVotesList.get(6));
        allRange.setRatingScoreWeightVote3(ratingScoreWeightVotesList.get(7));
        allRange.setRatingScoreWeightVote2(ratingScoreWeightVotesList.get(8));
        allRange.setRatingScoreWeightVote1(ratingScoreWeightVotesList.get(9));

        return allRange;
    }

    private AllAgeRatingVote allAgeRatingVoteHandler(ImdbRatings imdbRatings) {
        AllAgeRatingVote allAgeRatingVote = new AllAgeRatingVote();
        RatingVote ratingVote = new RatingVote();
        ratingVote = StringUtils.isNotEmpty(imdbRatings.getAgeAll()) == Boolean.TRUE ? ratingVoteTransfer(imdbRatings.getAgeAll()) : new RatingVote();
        allAgeRatingVote.setAgeAll(ratingVote);
        ratingVote = StringUtils.isNotEmpty(imdbRatings.getAgeLessThan18()) == Boolean.TRUE ? ratingVoteTransfer(imdbRatings.getAgeLessThan18()) : new RatingVote();
        allAgeRatingVote.setAgeLessThan18(ratingVote);
        ratingVote = StringUtils.isNotEmpty(imdbRatings.getAge1829()) == Boolean.TRUE ? ratingVoteTransfer(imdbRatings.getAge1829()) : new RatingVote();
        allAgeRatingVote.setAge1829(ratingVote);
        ratingVote = StringUtils.isNotEmpty(imdbRatings.getAge3044()) == Boolean.TRUE ? ratingVoteTransfer(imdbRatings.getAge3044()) : new RatingVote();
        allAgeRatingVote.setAge3044(ratingVote);
        ratingVote = StringUtils.isNotEmpty(imdbRatings.getAgeMoreThan45()) == Boolean.TRUE ? ratingVoteTransfer(imdbRatings.getAgeMoreThan45()) : new RatingVote();
        allAgeRatingVote.setAgeMoreThan45(ratingVote);
        return allAgeRatingVote;
    }

    private RatingVote ratingVoteTransfer(String ratingVote) {
        String[] split = ratingVote.split("\\|");
        return new RatingVote(Double.parseDouble(split[0]), Integer.parseInt(split[1]));
    }

    private AllSexRatingVote allSexRatingVoteHandler(ImdbRatings imdbRatings) {
        AllSexRatingVote allSexRatingVote = new AllSexRatingVote();
        RatingVote ratingVote = new RatingVote();
        ratingVote = StringUtils.isNotEmpty(imdbRatings.getMaleRatings()) == Boolean.TRUE ? ratingVoteTransfer(imdbRatings.getMaleRatings()) : new RatingVote();
        allSexRatingVote.setMaleRatings(ratingVote);
        ratingVote = StringUtils.isNotEmpty(imdbRatings.getFemaleRatings()) == Boolean.TRUE ? ratingVoteTransfer(imdbRatings.getFemaleRatings()) : new RatingVote();
        allSexRatingVote.setFemaleRatings(ratingVote);
        return allSexRatingVote;
    }
}