package com.sciatta.openmall.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yangxiaoyu on 2021/7/27<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 测试
 */
@RestController
public class TestController {
    @RequestMapping("test")
    public String test() {
        return "ok";
    }
}
