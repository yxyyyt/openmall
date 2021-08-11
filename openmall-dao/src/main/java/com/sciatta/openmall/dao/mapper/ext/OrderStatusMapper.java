package com.sciatta.openmall.dao.mapper.ext;

import com.sciatta.openmall.dao.pojo.query.OrderStatusCountsDaoQuery;
import org.apache.ibatis.annotations.Param;

/**
 * Created by yangxiaoyu on 2021/8/10<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * OrderStatusMapper
 */
public interface OrderStatusMapper extends com.sciatta.openmall.dao.mapper.mbg.OrderStatusMapper {
    Integer selectOrderStatusCounts(OrderStatusCountsDaoQuery orderStatusCountsDaoQuery);
}
