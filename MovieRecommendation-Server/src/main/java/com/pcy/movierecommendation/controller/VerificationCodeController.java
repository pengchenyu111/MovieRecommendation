package com.pcy.movierecommendation.controller;

import com.pcy.movierecommendation.core.constants.ErrorMessages;
import com.pcy.movierecommendation.core.model.ApiResponse;
import com.pcy.movierecommendation.service.VerificationCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 获取验证码的接口
 *
 * @author PengChenyu
 * @since 2020-12-20 00:05:27
 */
@RestController
@RequestMapping("api/recommendation/verificationCode")
@Api(value = "/verificationCode", tags = "VerificationCode")
public class VerificationCodeController {

    @Autowired
    VerificationCodeService verificationCodeService;

    @ApiOperation(value = "验证码", notes = "通过阿里云短信服务获取验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "phoneNumber", value = "用户手机号", required = true, dataType = "String")
    })
    @GetMapping("/{phoneNumber}")
    public ApiResponse getCode(@PathVariable("phoneNumber") String phoneNumber) {
        boolean isSuccess = verificationCodeService.getCode(phoneNumber);
        if (!isSuccess) {
            return ApiResponse.failed(ErrorMessages.VERIFICATION_FAIL);
        }
        return ApiResponse.success(ErrorMessages.VERIFICATION_SUCCESS);
    }


    @ApiOperation(value = "验证码", notes = "检查验证码是否正确")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "phoneNumber", value = "用户手机号", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "code", value = "用户填写的验证码", required = true, dataType = "String")
    })
    @GetMapping("/check/{phoneNumber}/{code}")
    public ApiResponse checkCode(@PathVariable("phoneNumber") String phoneNumber, @PathVariable("code") String code) {
        boolean isCorrect = verificationCodeService.checkCode(phoneNumber, code);
        if (!isCorrect) {
            return ApiResponse.failed(ErrorMessages.VERIFICATION_WRONG);
        }
        return ApiResponse.success(ErrorMessages.VERIFICATION_CORRECT);
    }
}
