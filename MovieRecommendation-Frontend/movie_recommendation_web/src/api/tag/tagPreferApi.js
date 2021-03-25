import httpFetch from "@/api/httpFetch";

/**
 * 根据用户id查询其喜欢的电影分类
 * @param userId
 * @returns {Promise<AxiosResponse<any>>}
 */
export const queryUserTagPreferById = (userId) => {
  const url = `tagPrefer/${userId}`
  return httpFetch.get(url)
}

export const queryUserTagPreferDetailById = (userId) => {
  const url = `tagPrefer/detail/${userId}`
  return httpFetch.get(url)
}

/**
 * 新增一个
 */
export const addUserTagPrefer = (request) => {
  const url = 'tagPrefer'
  return httpFetch.post(url, request)

}

/**
 * 修改一个
 */
export const updateUserTagPrefer = (request) => {
  const userId = request.userId
  const url = `tagPrefer/${userId}`
  return httpFetch.put(url, request)
}
