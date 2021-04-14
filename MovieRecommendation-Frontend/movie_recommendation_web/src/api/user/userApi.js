import httpFetch from "@/api/httpFetch";

/**
 * 用户登录
 */
export const changeInfo = (request) => {
  let userId = request.userId
  const url = `/movieUser/${userId}`
  return httpFetch.put(url, request)
}
