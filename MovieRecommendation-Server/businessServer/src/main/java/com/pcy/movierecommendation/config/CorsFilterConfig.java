package com.pcy.movierecommendation.config;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 负责跨域请求的过滤器
 *
 * @author PengChenyu
 * @since 2020-12-19 14:45:00
 */
@Component
public class CorsFilterConfig implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        //允许跨域访问的域
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        //允许使用的请求方法
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        //缓存此次请求的秒数
        response.setHeader("Access-Control-Max-Age", "3600");
        //允许自定义的头部
        response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With, userId,token");
        //是否允许请求带有验证信息
        response.setHeader("Access-Control-Allow-Credentials", "true");
        //隐私安全平台，网站向浏览器申明自己的隐私政策。
        response.setHeader("P3P", "CP=\"NON DSP COR CURa ADMa DEVa TAIa PSAa PSDa IVAa IVDa CONa HISa TELa OTPa OUR UNRa IND UNI COM NAV INT DEM CNT PRE LOC\"");

        response.setHeader("XDomainRequestAllowed", "1");

        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpStatus.OK.value());
            return;
        }
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

}

