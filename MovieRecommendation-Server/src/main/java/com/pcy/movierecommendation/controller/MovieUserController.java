package com.pcy.movierecommendation.controller;

import com.github.pagehelper.PageInfo;
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
     * 分页查询
     *
     * @param pageNum   当前页
     * @param pageSize  每页多少数据
     * @param movieUser 查询条件
     * @return 单条数据
     */
    @ApiOperation(value = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "当前页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页的数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "movieUser", value = "查询条件", required = true, dataType = "MovieUser")
    })
    @PostMapping("/page/{pageNum}/{pageSize}")
    public ApiResponse<PageInfo<MovieUser>> queryPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, @RequestBody MovieUser movieUser) {
        PageInfo<MovieUser> result = this.movieUserService.queryPage(pageNum, pageSize, movieUser);
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, result);
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


    /**
     * 用户修改个人信息
     *
     * @param movieUser 已修改的个人信息
     * @param userId    用户id
     * @return 单条用户数据
     */
    @ApiOperation(value = "用户修改个人信息", notes = "用户修改个人基础信息，此处某些信息无法修改，比如密码、用户唯一标识等")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "movieUser", value = "已修改的个人信息", required = true, dataType = "MovieUser")
    })
    @PutMapping("/{userId}")
    public ApiResponse<MovieUser> changePassword(@RequestBody MovieUser movieUser, @PathVariable("userId") int userId) {
        MovieUser updatedUser = movieUserService.update(movieUser);
        if (updatedUser == null) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.CHANGE_FAIL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.CHANGE_SUCCESS, updatedUser);
    }

    /**
     * 用户注册
     *
     * @param movieUser 要注册的的用户个人信息
     * @return 单条用户数据
     */
    @ApiOperation(value = "用户注册", notes = "注册一名新用户，验证码验证在 VerificationCodeController 中验证")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "movieUser", value = "用户个人信息", required = true, dataType = "MovieUser")
    })
    @PostMapping("/register")
    public ApiResponse<MovieUser> register(@RequestBody MovieUser movieUser) {
        MovieUser registeredMovieUser = movieUserService.register(movieUser);
        if (movieUser == null) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.REGISTER_FAIL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REGISTER_SUCCESS, registeredMovieUser);
    }

}