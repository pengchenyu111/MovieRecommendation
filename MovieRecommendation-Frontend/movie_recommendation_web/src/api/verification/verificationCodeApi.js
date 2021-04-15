import httpFetch from "@/api/httpFetch";

/**
 * 根据手机号获取验证码
 * @param phoneNumber
 * @returns {Promise<AxiosResponse<any>>}
 */
export const getCode = (phoneNumber) => {
  const url = `/verificationCode/${phoneNumber}`
  return httpFetch.get(url)
}
