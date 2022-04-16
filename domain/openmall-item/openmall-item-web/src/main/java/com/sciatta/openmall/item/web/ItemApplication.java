package com.sciatta.openmall.item.web;

import com.sciatta.openmall.item.service.client.OrderServiceFeignClient;
import com.sciatta.openmall.item.service.client.UserServiceFeignClient;
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
 * ItemApplication
 */
@MapperScan(basePackages = {"com.sciatta.openmall.item.mapper.ext"})
@ComponentScan(basePackages = {"com.sciatta.openmall"})
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableDiscoveryClient
@EnableFeignClients(clients = {
        UserServiceFeignClient.class,
        OrderServiceFeignClient.class
})
public class ItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemApplication.class, args);
    }
}
