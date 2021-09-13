package com.sciatta.openmall.api.config;

import com.sciatta.openmall.api.intercepter.UserTokenInterceptor;
import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Created by yangxiaoyu on 2021/8/13<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 静态资源映射
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    private final OpenMallConfig openMallConfig;
    private final UserTokenInterceptor userTokenInterceptor;
    
    public WebMvcConfig(OpenMallConfig openMallConfig, UserTokenInterceptor userTokenInterceptor) {
        this.openMallConfig = openMallConfig;
        this.userTokenInterceptor = userTokenInterceptor;
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
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userTokenInterceptor)
                .addPathPatterns("/mycomments/*")
                .addPathPatterns("/myorders/*")
                .addPathPatterns("/orders/*")
                .addPathPatterns("/shopCart/add")
                .addPathPatterns("/shopCart/del")
                .addPathPatterns("/address/*")
                .addPathPatterns("/user/*");
    }
    
    // ------------------------------------------------------------------------------------------
    
    // 多个请求参数，只要有一个校验失败，后面的其他参数就不做校验；对@RequestParam和@RequestBody都起作用
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true) // failFast 只要出现校验失败的情况，就立即结束校验，不再进行后续校验
                .buildValidatorFactory();
        
        return validatorFactory.getValidator();
    }
}

