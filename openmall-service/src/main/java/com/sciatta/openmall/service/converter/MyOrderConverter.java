package com.sciatta.openmall.service.converter;

import com.sciatta.openmall.dao.pojo.po.ext.ImageItemComment;
import com.sciatta.openmall.dao.pojo.po.mbg.Order;
import com.sciatta.openmall.dao.pojo.po.mbg.OrderStatus;
import com.sciatta.openmall.dao.pojo.query.OrderStatusCountsDaoQuery;
import com.sciatta.openmall.dao.pojo.query.OrderStatusDaoQuery;
import com.sciatta.openmall.service.pojo.dto.ImageItemCommentDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusCountsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/11<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * MyOrderConverter
 */
@Mapper
public abstract class MyOrderConverter {
    public static MyOrderConverter INSTANCE = Mappers.getMapper(MyOrderConverter.class);
    
    public abstract OrderStatusCountsDaoQuery toOrderStatusCountsDaoQuery(String userId, Integer orderStatus, Integer isComment);
    
    public abstract OrderStatusCountsDTO toOrderStatusCountsDTO(Integer waitPayCounts,
                                                                Integer waitDeliverCounts,
                                                                Integer waitReceiveCounts,
                                                                Integer waitCommentCounts);
    
    
    public abstract OrderStatusDaoQuery toOrderStatusDaoQuery(String userId, Integer isDelete, List<Integer> orderStatuses);
    
    public abstract OrderStatus toReceiveOrderStatus(Integer orderStatus, Date successTime);
    
    public abstract Order toDeleteOrder(Integer isDelete, Date updatedTime);
    
    public abstract Order toCommentOrder(String id, Integer isComment);
    
    public abstract OrderStatus toCommentOrderStatus(String orderId, Date commentTime);
    
    public abstract List<ImageItemCommentDTO> imageItemCommentListToImageItemCommentDTOList(List<ImageItemComment> imageItemCommentList);
}
