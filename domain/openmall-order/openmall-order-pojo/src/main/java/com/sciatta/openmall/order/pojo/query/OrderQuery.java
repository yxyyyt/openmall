package com.sciatta.openmall.order.pojo.query;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/10<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * OrderQuery
 */
@Data
public class OrderQuery implements Serializable {
    
    private static final long serialVersionUID = 5345810943670031697L;
    
    @NotBlank(message = "用户标识不能为空")
    private String userId;
    
    @NotBlank(message = "商品规格标识不能为空")
    private String itemSpecIds;
    
    @NotBlank(message = "用户地址标识不能为空")
    private String addressId;
    
    @Min(value = 1, message = "支付方式仅支持微信或支付宝")
    @Max(value = 2, message = "支付方式仅支持微信或支付宝")
    @NotNull(message = "支付方式仅支持微信或支付宝")
    private Integer payMethod;
    
    private String leftMsg;
}
