package com.sciatta.openmall.api.converter;

import com.sciatta.openmall.api.pojo.vo.OrderStatusCountsVO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusCountsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by yangxiaoyu on 2021/8/11<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * MyOrderConverter
 */
@Mapper
public abstract class MyOrderConverter {
    public static MyOrderConverter INSTANCE = Mappers.getMapper(MyOrderConverter.class);
    
    public abstract OrderStatusCountsVO orderStatusCountsDTOToOrderStatusCountsVO (OrderStatusCountsDTO orderStatusCountsDTO);
}
