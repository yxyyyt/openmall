package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.config.OpenMallConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by yangxiaoyu on 2021/7/27<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 测试
 */
@RestController
public class TestController {
    
    private final OpenMallConfig openMallConfig;
    
    public TestController(OpenMallConfig openMallConfig) {
        this.openMallConfig = openMallConfig;
    }
    
    @RequestMapping("test")
    public String test() {
        InetAddress localHost = null;
        StringBuilder testMessage = new StringBuilder();
        
        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException ignored) {
        }
        
        if (localHost != null) {
            testMessage
                    .append("HostAddress { " + localHost.getHostAddress() + " } ")
                    .append("HostName { " + localHost.getHostName() + " } ")
                    .append("Sid { " + openMallConfig.getSid() + " }");
        }
        
        return testMessage.toString();
    }
}
