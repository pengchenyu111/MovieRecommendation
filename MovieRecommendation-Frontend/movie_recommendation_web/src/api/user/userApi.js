import httpFetch from "@/api/httpFetch";

/**
 * 用户修改个人信息
 */
export const changeInfo = (request) => {
  let userId = request.userId
  const url = `/movieUser/${userId}`
  return httpFetch.put(url, request)
}

/**
 * 修改密码
 */
export const changePwd = (request) => {
  const url = '/movieUser/changePassword'
  return httpFetch.post(url, request)
}
