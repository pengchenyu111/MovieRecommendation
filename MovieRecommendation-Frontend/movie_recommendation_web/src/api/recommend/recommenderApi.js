import httpFetch from "@/api/httpFetch";

/**
 * 查询历史Top20电影
 * @returns {Promise<AxiosResponse<any>>}
 */
export const queryHistoryTop20 = () => {
  const url = `historyTop20`
  return httpFetch.get(url)
}

/**
 * 查询近期Top20电影
 * @returns {Promise<AxiosResponse<any>>}
 */
export const queryRecentlyTop20 = () => {
  const url = `recentlyTop20`
  return httpFetch.get(url)
}

/**
 * 基于ALS的用户电影推荐
 * @returns {Promise<AxiosResponse<any>>}
 */
export const queryALSUserRecs = (userId) => {
  const url = `alsUserRecs/${userId}`
  return httpFetch.get(url)
}

/**
 * 实时评分电影推荐
 * @returns {Promise<AxiosResponse<any>>}
 */
export const queryStreamRatingRecs = (userId) => {
  const url = `ratingRecs/${userId}`
  return httpFetch.get(url)
}

/**
 * 用户感兴趣电影标签电影推荐
 * @returns {Promise<AxiosResponse<any>>}
 */
export const queryUserPreferGenreRecs = (userId) => {
  const url = `userPreferGenreTop10/${userId}`
  return httpFetch.get(url)
}

/**
 * 类别电影的Top10
 * @returns {Promise<AxiosResponse<any>>}
 */
export const queryPerTagRecs = (genre) => {
  const url = 'genreTop10'
  return httpFetch.post(url,{genre: genre})
}

/**
 * 基于内容的电影推荐
 * @returns {Promise<AxiosResponse<any>>}
 */
export const queryContentRecs = (doubanId) => {
  const url = `content/${doubanId}`
  return httpFetch.get(url)
}

/**
 * 基于Item-CF的电影推荐
 * @returns {Promise<AxiosResponse<any>>}
 */
export const queryItemCFRecs = (doubanId) => {
  const url = `itemCFRecs/${doubanId}`
  return httpFetch.get(url)
}
