package com.sciatta.openmall.api.converter;

import com.sciatta.openmall.api.pojo.vo.OrderItemVO;
import com.sciatta.openmall.api.pojo.vo.OrderStatusItemVO;
import com.sciatta.openmall.api.pojo.vo.OrderStatusVO;
import com.sciatta.openmall.service.pojo.dto.OrderItemDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusItemDTO;
import com.sciatta.openmall.service.pojo.query.OrderQuery;
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
    
    public abstract OrderQuery toOrderQuery(com.sciatta.openmall.api.pojo.query.OrderQuery orderQuery);
    
    public abstract OrderStatusVO toOrderStatusVO(OrderStatusDTO orderStatusDTO);
    
    public abstract List<OrderStatusVO> orderStatusDTOListToOrderStatusVOList(List<OrderStatusDTO> orderStatusDTOList);
    
    public abstract OrderStatusItemVO orderStatusItemDTOToOrderStatusItemVO(OrderStatusItemDTO orderStatusItemDTO);
    
    public abstract List<OrderStatusItemVO> orderStatusItemDTOListToOrderStatusItemVOList(List<OrderStatusItemDTO> orderStatusItemDTOList);
    
    public abstract List<OrderItemVO> toOrderItemVO(List<OrderItemDTO> orderItemDTOList);
}
