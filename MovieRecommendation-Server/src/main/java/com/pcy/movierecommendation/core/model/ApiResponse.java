package com.pcy.movierecommendation.core.model;

import com.pcy.movierecommendation.core.constants.ErrorMessages;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 接口返回对象
 *
 * @author PengChenyu
 * @since 2020-12-18 17:42:00
 */
@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private Boolean success;

    private String message;

    private T data;

    /**
     * 请求成功
     *
     * @param data 数据
     * @return 请求成功的message + 数据data
     */
    public static <T> ApiResponse success(T data) {
        return ApiResponse.success(ErrorMessages.REQUEST_SUCCESS, data);
    }

    /**
     * 请求成功
     *
     * @param message 自定义message
     * @param data    数据
     * @return 成功bool值 + 请求成功的message + 数据data
     */
    public static <T> ApiResponse success(String message, T data) {
        return new ApiResponse<>(Boolean.TRUE, message, data);
    }

    /**
     * 请求失败
     *
     * @return 请求失败的message
     */
    public static ApiResponse failed() {
        return ApiResponse.failed(ErrorMessages.REQUEST_FAIL);
    }

    /**
     * 请求失败
     *
     * @param message 自定义message
     * @return 失败bool值 + 请求失败的message + 空数据data
     */
    public static ApiResponse failed(String message) {
        return new ApiResponse<>(Boolean.FALSE, message, null);
    }
}