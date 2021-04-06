import httpFetch from "@/api/httpFetch";

/**
 * 根据豆瓣id分页查询
 *
 * @param doubanId
 * @param pageNum
 * @param pageSize
 */
export const queryByDoubanIdPage = (doubanId, pageNum, pageSize) => {
  const url = `movieReviews/doubanId/${doubanId}/page/${pageNum}/${pageSize}`
  return httpFetch.get(url)
}

/**
 * 点赞
 *
 * @param reviewId
 * @returns {Promise<AxiosResponse<any>>}
 */
export const reviewAgree = (reviewId) => {
  const url = `movieReviews/agree/${reviewId}`
  return httpFetch.get(url)
}
