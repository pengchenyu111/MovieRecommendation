package com.pcy.movierecommendation;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author PengChenyu
 * @since 2020-12-18 18:59:03
 */
@Slf4j
@ComponentScan("com.pcy.movierecommendation")
public class BaseTest {

    /**
     * 打印测试结果
     */
    protected void print(Object obj) {
        log.info("测试结果: {}", obj == null ? "无返回" : JSON.toJSONString(obj));
    }
}
