package com.sciatta.openmall.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by yangxiaoyu on 2021/7/27<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 应用程序入口
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.sciatta.openmall.dao.mapper.ext"})
@ComponentScan(basePackages = {"com.sciatta.openmall"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
