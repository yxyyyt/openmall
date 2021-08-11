package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.converter.MyOrderConverter;
import com.sciatta.openmall.api.pojo.vo.OrderStatusCountsVO;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.service.MyOrderService;
import com.sciatta.openmall.service.pojo.dto.OrderStatusCountsDTO;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yangxiaoyu on 2021/8/11<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 我的订单
 */
@RestController
@RequestMapping("myorders")
public class MyOrderController {
    private final MyOrderService myOrderService;
    
    public MyOrderController(MyOrderService myOrderService) {
        this.myOrderService = myOrderService;
    }
    
    @PostMapping("statusCounts")
    public JSONResult statusCounts(@RequestParam String userId) {
        if (!StringUtils.hasText(userId)) {
            return JSONResult.errorUsingMessage("用户不能为空");
        }
        
        OrderStatusCountsDTO orderStatusCountsDTO = myOrderService.getOrderStatusCounts(userId);
        OrderStatusCountsVO orderStatusCountsVO = MyOrderConverter.INSTANCE.orderStatusCountsDTOToOrderStatusCountsVO(orderStatusCountsDTO);
        
        return JSONResult.success(orderStatusCountsVO);
    }
}
