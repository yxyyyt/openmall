package com.sciatta.openmall.api.converter;

import com.sciatta.openmall.service.pojo.query.ShopCartQuery;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/9/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ShopCartConverter
 */
@Mapper
public abstract class ShopCartConverter {
    public static ShopCartConverter INSTANCE = Mappers.getMapper(ShopCartConverter.class);
    
    public abstract List<ShopCartQuery> toServiceShopCartQuery(
            List<com.sciatta.openmall.api.pojo.query.ShopCartQuery> shopCartQueryList);
    
    public abstract List<com.sciatta.openmall.api.pojo.query.ShopCartQuery> toApiShopCartQuery(
            List<ShopCartQuery> shopCartQueryList);
}
