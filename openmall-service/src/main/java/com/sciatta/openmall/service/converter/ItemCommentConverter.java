package com.sciatta.openmall.service.converter;

import com.sciatta.openmall.dao.pojo.po.mbg.ItemComment;
import com.sciatta.openmall.service.pojo.query.OrderItemCommentServiceQuery;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.n3r.idworker.Sid;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/12<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemCommentConverter
 */
@Mapper
public abstract class ItemCommentConverter {
    public static ItemCommentConverter INSTANCE = Mappers.getMapper(ItemCommentConverter.class);
    
    public abstract ItemComment orderItemCommentServiceQueryToItemComment(
            OrderItemCommentServiceQuery orderItemCommentServiceQuery, String id, String userId, Date createdTime, Date updatedTime);
    
    public List<ItemComment> orderItemCommentServiceQueryListToItemComment(
            List<OrderItemCommentServiceQuery> orderItemCommentServiceQueryList, String userId) {
        
        List<ItemComment> itemCommentList = new ArrayList<>();
        
        for (OrderItemCommentServiceQuery orderItemCommentServiceQuery : orderItemCommentServiceQueryList) {
            itemCommentList.add(orderItemCommentServiceQueryToItemComment(
                    orderItemCommentServiceQuery, Sid.nextShort(), userId, new Date(), new Date()));
        }
        
        return itemCommentList;
    }
}
