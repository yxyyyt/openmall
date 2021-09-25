package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.converter.UserAddressConverter;
import com.sciatta.openmall.api.pojo.query.UserAddressQuery;
import com.sciatta.openmall.api.pojo.vo.UserAddressVO;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.service.UserAddressService;
import com.sciatta.openmall.service.pojo.dto.UserAddressDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/9<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 用户地址
 */
@Validated
@RestController
@RequestMapping("address")
public class MyAddressController {
    private final UserAddressService userAddressService;
    
    public MyAddressController(UserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }
    
    @PostMapping("list")
    public JSONResult list(@RequestParam @NotBlank(message = "用户标识不能为空") String userId) {
        
        List<UserAddressDTO> userAddressDTOList = userAddressService.queryByUserId(userId);
        List<UserAddressVO> userAddressVOList = UserAddressConverter.INSTANCE.toUserAddressVO(userAddressDTOList);
        
        return JSONResult.success(userAddressVOList);
    }
    
    @PostMapping("/add")
    public JSONResult add(@RequestBody @Validated(UserAddressQuery.Add.class) UserAddressQuery userAddressQuery) {
        
        userAddressService.createUserAddress(UserAddressConverter.INSTANCE.toUserAddressQuery(userAddressQuery));
        
        return JSONResult.success();
    }
    
    @PostMapping("/update")
    public JSONResult update(@RequestBody @Validated(UserAddressQuery.Update.class) UserAddressQuery userAddressQuery) {
        
        userAddressService.updateUserAddress(UserAddressConverter.INSTANCE.toUserAddressQuery(userAddressQuery));
        
        return JSONResult.success();
    }
    
    @PostMapping("/delete")
    public JSONResult delete(
            @RequestParam @NotBlank(message = "用户标识不能为空") String userId,
            @RequestParam @NotBlank(message = "用户地址标识不能为空") String addressId) {
        
        userAddressService.deleteUserAddressByUserIdAndAddressId(userId, addressId);
        
        return JSONResult.success();
    }
    
    @PostMapping("/setDefault")
    public JSONResult setDefault(
            @RequestParam @NotBlank(message = "用户标识不能为空") String userId,
            @RequestParam @NotBlank(message = "用户地址标识不能为空") String addressId) {
        
        userAddressService.updateUserAddressToBeDefault(userId, addressId);
        
        return JSONResult.success();
    }
    
}
