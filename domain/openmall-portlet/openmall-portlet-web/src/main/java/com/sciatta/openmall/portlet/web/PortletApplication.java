package com.sciatta.openmall.portlet.web;

import com.sciatta.openmall.portlet.service.client.ItemCategoryServiceFeignClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by Rain on 2022/3/21<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * PortletApplication
 */
@MapperScan(basePackages = {"com.sciatta.openmall.portlet.mapper.ext"})
@ComponentScan(basePackages = {"com.sciatta.openmall"})
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableDiscoveryClient
@EnableFeignClients(clients = {
        ItemCategoryServiceFeignClient.class
})
public class PortletApplication {
    public static void main(String[] args) {
        SpringApplication.run(PortletApplication.class, args);
    }
}
