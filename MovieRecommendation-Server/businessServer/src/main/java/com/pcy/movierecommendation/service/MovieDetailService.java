package com.pcy.movierecommendation.service;

import com.github.pagehelper.PageInfo;
import com.pcy.movierecommendation.entity.movieDetail.MovieDetail;
import com.pcy.movierecommendation.entity.movieDetail.MovieDetailSearchRequest;
import com.pcy.movierecommendation.es.ElasticSearchVo;

import java.util.List;

/**
 * (MovieDetail)表服务接口
 *
 * @author PengChenyu
 * @since 2020-12-21 21:41:52
 */
public interface MovieDetailService {

    /**
     * 通过ID查询单条数据
     *
     * @param doubanId 主键
     * @return 实例对象
     */
    MovieDetail queryById(Integer doubanId);

    /**
     * 通过多个ID查询数据
     *
     * @param doubanIdList 主键列表
     * @return 对象列表
     */
    List<MovieDetail> queryByIdList(List<Integer> doubanIdList);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MovieDetail> queryAllByLimit(int offset, int limit);

    /**
     * 查询全部数据
     *
     * @return 对象列表
     */
    List<MovieDetail> queryAllMovieDetails();


    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    int count();


    /**
     * 新增数据
     *
     * @param movieDetail 实例对象
     * @return 实例对象
     */
    MovieDetail insert(MovieDetail movieDetail);

    /**
     * 修改数据
     *
     * @param movieDetail 实例对象
     * @return 实例对象
     */
    MovieDetail update(MovieDetail movieDetail);

    /**
     * 通过主键删除数据
     *
     * @param doubanId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer doubanId);

    /**
     * 分页获取电影详情
     *
     * @param pageNum  当前页
     * @param pageSize 每页多少数据
     * @return 分页数据
     */
    PageInfo<MovieDetail> queryPageMovie(int pageNum, int pageSize);

    /**
     * 分页查询
     *
     * @param pageNum     当前页
     * @param pageSize    每页多少数据
     * @param movieDetail 查询条件
     * @return 分页数据
     */
    PageInfo<MovieDetail> queryPage(int pageNum, int pageSize, MovieDetail movieDetail);


    /**
     * 电影搜索
     *
     * @param keyword  搜索关键词
     * @param pageNum  第几页
     * @param pageSize 每页大小
     * @return ElasticSearchVo<MovieDetail>
     */
    ElasticSearchVo<MovieDetail> searchMovie(String keyword, int pageNum, int pageSize);

    /**
     * douban_id精准查询
     *
     * @param doubanId 豆瓣id
     * @return 电影数据
     */
    MovieDetail searchMovieByDoubanId(int doubanId);

    /**
     * 类豆瓣标签搜索
     *
     * @param movieDetailSearchRequest 请求条件实体
     * @return ES内电影数据
     */
    ElasticSearchVo<MovieDetail> searchByTags(MovieDetailSearchRequest movieDetailSearchRequest);
}