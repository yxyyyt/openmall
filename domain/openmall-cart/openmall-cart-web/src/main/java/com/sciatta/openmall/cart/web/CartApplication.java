package com.sciatta.openmall.cart.web;

import com.sciatta.openmall.cart.service.client.ItemServiceFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by Rain on 2022/3/21<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * CartApplication
 */
@ComponentScan(basePackages = {"com.sciatta.openmall"})
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableDiscoveryClient
@EnableFeignClients(clients = {
        ItemServiceFeignClient.class
})
public class CartApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class, args);
    }
}
