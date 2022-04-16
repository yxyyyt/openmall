package com.sciatta.openmall.order.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/10<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * MerchantOrderDTO
 */
@Data
public class MerchantOrderDTO implements Serializable {

    private static final long serialVersionUID = 4024849971294130068L;

    private String merchantOrderId;         // 订单主键
    private String merchantUserId;          // 用户主键
    private Integer amount;                 // 实际支付总金额（包含所支付的订单费和邮费总额）
    private Integer payMethod;              // 支付方式 1:微信 2:支付宝
    private String returnUrl;               // 支付成功后的回调地址
}
