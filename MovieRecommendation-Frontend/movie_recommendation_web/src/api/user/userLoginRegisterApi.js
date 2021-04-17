import httpFetch from "@/api/httpFetch";

/**
 * 用户登录
 */
export const login = (account, password) => {
  const url = '/movieUser/login'
  const request = {account: account, password: password}
  return httpFetch.post(url, request)
}

/**
 * 用户注册
 */
export const register = (request) => {
  const url = '/movieUser/register'
  return httpFetch.post(url, request)
}
