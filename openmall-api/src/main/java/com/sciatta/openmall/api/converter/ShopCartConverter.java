package com.sciatta.openmall.api.converter;

import com.sciatta.openmall.api.pojo.query.ItemShopCartQuery;
import com.sciatta.openmall.service.pojo.query.ShopCartAddServiceQuery;
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
    
    public abstract List<ShopCartAddServiceQuery> shopCartAddApiQueryListToShopCartAddServiceQueryList(List<ItemShopCartQuery> itemShopCartQueryList);
    
    public abstract List<ItemShopCartQuery> shopCartAddServiceQueryListToShopCartAddApiQueryList(List<ShopCartAddServiceQuery> shopCartAddServiceQueryList);
}
