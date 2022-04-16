package com.sciatta.openmall.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Created by yangxiaoyu on 2021/7/29<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 解决跨域问题
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", buildConfig()); // 对接口配置跨域设置
        
        return new CorsFilter(corsSource);
    }
    
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        
        corsConfiguration.addAllowedOrigin("*");        // 设置访问源地址
        corsConfiguration.addAllowedHeader("*");        // 设置访问源请求头
        corsConfiguration.addAllowedMethod("*");        // 设置访问源请求方法
        corsConfiguration.setAllowCredentials(true);    // 设置是否发送cookie信息
        
        return corsConfiguration;
    }
}
