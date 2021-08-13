package com.sciatta.openmall.api.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by yangxiaoyu on 2021/8/13<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 静态资源映射
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    private final OpenMallConfig openMallConfig;
    
    public WebMvcConfig(OpenMallConfig openMallConfig) {
        this.openMallConfig = openMallConfig;
    }
    
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 相对路径处理
        // child 相对于 /base/ => /base/child
        // child 相对于 /base  => /child
        registry.addResourceHandler("/**")
                .addResourceLocations("file:" + openMallConfig.getUpload().getImageUserFaceLocation());  // 映射本地静态资源
    }
    
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}

