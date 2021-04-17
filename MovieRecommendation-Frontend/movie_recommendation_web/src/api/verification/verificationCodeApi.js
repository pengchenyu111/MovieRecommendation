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

/**
 * 校验验证码
 * @param phoneNumber
 * @Param code
 * @returns {Promise<AxiosResponse<any>>}
 */
export const checkCode = (phoneNumber, code) => {
  const url = `/verificationCode/check/${phoneNumber}/${code}`
  return httpFetch.get(url)
}
