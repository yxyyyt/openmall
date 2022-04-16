package com.sciatta.openmall.search.web;

import com.sciatta.openmall.search.service.client.ItemServiceFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by Rain on 2022/3/21<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * SearchApplication
 */
@ComponentScan(basePackages = {"com.sciatta.openmall"})
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableDiscoveryClient
@EnableFeignClients(clients = {
        ItemServiceFeignClient.class
})
public class SearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }
}
