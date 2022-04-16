package com.sciatta.openmall.web.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by yangxiaoyu on 2021/10/13<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ElasticSearchConfig
 */
@Configuration
public class ElasticSearchConfig {
    @PostConstruct
    void init() {
        // fix netty exception: java.lang.IllegalStateException: availableProcessors is already set to [8], rejecting [8]
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }
}
