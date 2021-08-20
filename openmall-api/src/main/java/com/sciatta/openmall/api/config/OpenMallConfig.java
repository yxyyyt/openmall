package com.sciatta.openmall.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yangxiaoyu on 2021/8/13<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * OpenMallConfig
 */
@Configuration
@ConfigurationProperties(prefix = "openmall")
@Data
public class OpenMallConfig {
    private String sid;
    private UploadConfig upload = new UploadConfig();
    
    @Data
    public static class UploadConfig {
        private String imageUserFaceLocation;
        private String imageServerUrl;
    }
}
