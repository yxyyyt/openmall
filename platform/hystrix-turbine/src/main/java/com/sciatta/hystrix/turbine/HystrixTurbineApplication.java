package com.sciatta.hystrix.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * Created by Rain on 2022/2/25<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * HystrixTurbineApplication
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableTurbine
public class HystrixTurbineApplication {
    public static void main(String[] args) {
        SpringApplication.run(HystrixTurbineApplication.class, args);
    }
}
