package com.sciatta.openmall.order.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/10<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * OrderDTO
 */
@Data
public class OrderDTO implements Serializable {
    
    private static final long serialVersionUID = -3650235366887826546L;
    
    private String orderId;
    private Integer isComment;
    private Integer isDelete;

    private MerchantOrderDTO merchantOrderDTO;  // 商户订单

    private String unPaidShopCart;  // 购物车中未支付商品Json
}
