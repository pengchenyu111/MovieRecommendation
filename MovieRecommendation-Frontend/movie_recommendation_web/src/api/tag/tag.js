import httpFetch from "@/api/httpFetch";

/**
 * 查询所有电影标签
 * @returns {Promise<AxiosResponse<any>>}
 */
export const queryAllTags = () => {
  const url = `movieTag`
  return httpFetch.get(url)
}
