package com.sciatta.openmall.web.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Created by yangxiaoyu on 2021/8/13<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * Web相关配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private OpenMallConfig openMallConfig;

    // ------------------------------------------------------------------------------------------
    // 映射本地静态资源
    // ------------------------------------------------------------------------------------------
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 相对路径处理
        // child 相对于 /base/ => /base/child
        // child 相对于 /base  => /child
        registry.addResourceHandler("/**")
                .addResourceLocations("file:" + openMallConfig.getUpload().getImageUserFaceLocation());
    }

    // ------------------------------------------------------------------------------------------
    // 请求参数验证
    // ------------------------------------------------------------------------------------------
    @Bean
    public Validator validator() {
        // failFast 多个请求参数，只要有一个校验失败，后面的其他参数就不做校验，立即结束校验
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure().failFast(true)
                .buildValidatorFactory();

        return validatorFactory.getValidator();
    }
}

