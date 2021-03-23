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
