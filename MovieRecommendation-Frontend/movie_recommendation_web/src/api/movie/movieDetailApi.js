import httpFetch from "@/api/httpFetch";

/**
 * 搜索，电影名/导演/演员
 *
 * @param pageNum  当前页
 * @param pageSize 每页多少数据
 * @param keyword  用户搜索的关键字
 * @returns {Promise<AxiosResponse<any>>}
 */
export const searchMovie = (pageNum, pageSize, keyword) => {
  const url = `movieDetail/search/${pageNum}/${pageSize}`
  return httpFetch.post(url, {keyword: keyword})
}

/**
 * 类豆瓣标签搜索
 *
 * @param searchRequest
 * @returns {Promise<AxiosResponse<any>>}
 */
export const searchByTags = (searchRequest) => {
  const url = 'movieDetail/searchByTags'
  return httpFetch.post(url, searchRequest)
}


/**
 * 豆瓣id查询电影详情
 *
 * @param doubanId
 * @returns {Promise<AxiosResponse<any>>}
 */
export const searchByDoubanId = (doubanId) => {
  const url = `movieDetail/${doubanId}`
  return httpFetch.get(url)
}
