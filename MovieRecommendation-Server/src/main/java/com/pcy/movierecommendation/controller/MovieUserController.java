package com.pcy.movierecommendation.controller;

import com.pcy.movierecommendation.core.constants.ErrorMessages;
import com.pcy.movierecommendation.core.model.ApiResponse;
import com.pcy.movierecommendation.entity.movieUser.MovieUser;
import com.pcy.movierecommendation.service.MovieUserService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * (MovieUser)表控制层
 *
 * @author PengChenyu
 * @since 2020-12-18 17:42:00
 */
@RestController
@RequestMapping("api/recommendation/movieUser")
@Api(value = "/movieUser", tags = "movieUser")
public class MovieUserController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private MovieUserService movieUserService;

    /**
     * 通过主键查询单条数据
     *
     * @param userId 主键
     * @return 单条数据
     */
    @ApiOperation(value = "主键查询", notes = "通过主键查询单条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, dataType = "Integer")
    })
    @GetMapping("/{userId}")
    public ApiResponse<MovieUser> selectOne(@PathVariable("userId") Integer userId) {
        MovieUser movieUser = this.movieUserService.queryById(userId);
        if (movieUser == null) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, movieUser);
    }

    /**
     * 用户登录请求
     *
     * @param map 账号和密码
     * @return 单条用户数据
     */
    @ApiOperation(value = "用户登录", notes = "用户输入账号和密码，进行验证登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "map", value = "用户账号和密码", required = true, dataType = "Map")
    })
    @PostMapping("/login")
    public ApiResponse<MovieUser> login(@RequestBody Map<String, String> map) {
        String account = map.get("account");
        String password = map.get("password");
        MovieUser movieUser = movieUserService.login(account, password);
        if (movieUser == null) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.LOGIN_ACCOUNT_PASSWORD_WRONG, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.LOGIN_SUCCESS, movieUser);

    }


    /**
     * 用户修改密码请求
     *
     * @param map 账号 + 验证码 + 新密码 + 确认密码
     * @return 单条用户数据
     */
    @ApiOperation(value = "用户修改密码", notes = "用户输入账号、验证码和新密码，进行密码修改")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "map", value = "账号、验证码、新密码、确认密码", required = true, dataType = "Map")
    })
    @PostMapping("/changePassword")
    public ApiResponse<MovieUser> changePassword(@RequestBody Map<String, String> map) {
        String account = map.get("account");
        String verifyCode = map.get("verifyCode");
        String newPassword = map.get("newPassword");
        String confirmPassword = map.get("confirmPassword");
        MovieUser movieUser = movieUserService.changePassword(account, verifyCode, newPassword, confirmPassword);
        if (movieUser == null) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.CHANGE_FAIL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.CHANGE_SUCCESS, movieUser);
    }

}