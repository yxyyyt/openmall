package com.sciatta.openmall.api;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Created by yangxiaoyu on 2021/8/15<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * [5-5] War启动入口
 */
public class WarApplication extends SpringBootServletInitializer {
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }
}
