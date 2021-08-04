package com.sciatta.openmall.service;

import com.sciatta.openmall.service.pojo.dto.ItemDTO;
import com.sciatta.openmall.service.pojo.dto.ItemImageDTO;
import com.sciatta.openmall.service.pojo.dto.ItemParamDTO;
import com.sciatta.openmall.service.pojo.dto.ItemSpecDTO;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemService
 */
public interface ItemService {
    ItemDTO queryItemById(String id);
    
    List<ItemImageDTO> queryItemImagesByItemId(String itemId);
    
    List<ItemSpecDTO> queryItemSpecsByItemId(String itemId);
    
    ItemParamDTO queryItemParamByItemId(String itemId);
}
