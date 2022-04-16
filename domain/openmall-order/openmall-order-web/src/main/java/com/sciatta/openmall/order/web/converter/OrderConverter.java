package com.sciatta.openmall.order.web.converter;

import com.sciatta.openmall.order.pojo.dto.OrderItemDTO;
import com.sciatta.openmall.order.pojo.dto.OrderStatusCountsDTO;
import com.sciatta.openmall.order.pojo.dto.OrderStatusDTO;
import com.sciatta.openmall.order.pojo.query.OrderItemVO;
import com.sciatta.openmall.order.pojo.query.OrderStatusCountsVO;
import com.sciatta.openmall.order.pojo.query.OrderStatusVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/10<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * OrderConverter
 */
@Mapper
public abstract class OrderConverter {
    public static OrderConverter INSTANCE = Mappers.getMapper(OrderConverter.class);

    public abstract OrderStatusVO toOrderStatusVO(OrderStatusDTO orderStatusDTO);
    
    public abstract List<OrderStatusVO> toOrderStatusVO(List<OrderStatusDTO> orderStatusDTOList);
    
    public abstract List<OrderItemVO> toOrderItemVO(List<OrderItemDTO> orderItemDTOList);
    
    public abstract OrderStatusCountsVO toOrderStatusCountsVO(OrderStatusCountsDTO orderStatusCountsDTO);
}
