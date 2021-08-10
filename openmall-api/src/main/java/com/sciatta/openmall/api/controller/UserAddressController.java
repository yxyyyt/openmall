package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.converter.UserAddressConverter;
import com.sciatta.openmall.api.pojo.query.UserAddressAddApiQuery;
import com.sciatta.openmall.api.pojo.query.UserAddressUpdateApiQuery;
import com.sciatta.openmall.api.pojo.vo.UserAddressVO;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.common.utils.ValidCheckUtils;
import com.sciatta.openmall.service.UserAddressService;
import com.sciatta.openmall.service.pojo.dto.UserAddressDTO;
import com.sciatta.openmall.service.pojo.query.UserAddressAddServiceQuery;
import com.sciatta.openmall.service.pojo.query.UserAddressUpdateServiceQuery;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/9<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 用户地址
 */
@RestController
@RequestMapping("address")
public class UserAddressController {
    private final UserAddressService userAddressService;
    
    public UserAddressController(UserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }
    
    @PostMapping("list")
    public JSONResult list(@RequestParam String userId) {
        if (!StringUtils.hasText(userId)) {
            return JSONResult.errorUsingMessage("用户不能为空");
        }
        
        List<UserAddressDTO> userAddressDTOList = userAddressService.queryByUserId(userId);
        List<UserAddressVO> userAddressVOList = UserAddressConverter.INSTANCE.userAddressDTOListToUserAddressVOList(userAddressDTOList);
        
        return JSONResult.success(userAddressVOList);
    }
    
    @PostMapping("/add")
    public JSONResult add(@RequestBody UserAddressAddApiQuery userAddressAddApiQuery) {
        
        JSONResult result = checkUserAddress(userAddressAddApiQuery);
        if (!result.isSuccess()) {
            return result;
        }
        
        UserAddressAddServiceQuery userAddressAddServiceQuery =
                UserAddressConverter.INSTANCE.userAddressAddApiQueryToUserAddressAddServiceQuery(userAddressAddApiQuery);
        
        userAddressService.createUserAddress(userAddressAddServiceQuery);
        
        return JSONResult.success();
    }
    
    @PostMapping("/update")
    public JSONResult update(@RequestBody UserAddressUpdateApiQuery userAddressUpdateApiQuery) {
        JSONResult result = checkUserAddress(userAddressUpdateApiQuery);
        if (!result.isSuccess()) {
            return result;
        }
        
        UserAddressUpdateServiceQuery userAddressUpdateServiceQuery =
                UserAddressConverter.INSTANCE.userAddressUpdateApiQueryToUserAddressUpdateServiceQuery(userAddressUpdateApiQuery);
        userAddressService.updateUserAddress(userAddressUpdateServiceQuery);
        
        return JSONResult.success();
    }
    
    @PostMapping("/delete")
    public JSONResult delete(
            @RequestParam String userId,
            @RequestParam String addressId) {
        
        if (!StringUtils.hasText(userId)) {
            return JSONResult.errorUsingMessage("用户不能为空");
        }
        
        if (!StringUtils.hasText(addressId)) {
            return JSONResult.errorUsingMessage("用户地址不能为空");
        }
        
        userAddressService.deleteUserAddressByUserIdAndAddressId(userId, addressId);
        
        return JSONResult.success();
    }
    
    @PostMapping("/setDefault")
    public JSONResult setDefalut(@RequestParam String userId, @RequestParam String addressId) {
        
        if (!StringUtils.hasText(userId)) {
            return JSONResult.errorUsingMessage("用户不能为空");
        }
        
        if (!StringUtils.hasText(addressId)) {
            return JSONResult.errorUsingMessage("用户地址不能为空");
        }
        
        userAddressService.updateUserAddressToBeDefault(userId, addressId);
        return JSONResult.success();
    }
    
    private JSONResult checkUserAddress(UserAddressAddApiQuery userAddressAddApiQuery) {
        JSONResult result;
        
        if (!(result = checkReceiver(userAddressAddApiQuery.getReceiver())).isSuccess() ||
                !(result = checkMobile(userAddressAddApiQuery.getMobile())).isSuccess() ||
                !(result = checkAddressInfo(userAddressAddApiQuery.getProvince(),
                        userAddressAddApiQuery.getCity(),
                        userAddressAddApiQuery.getDistrict(),
                        userAddressAddApiQuery.getDetail())).isSuccess()
        ) {
            return result;
        }
        
        return JSONResult.success();
    }
    
    private JSONResult checkUserAddress(UserAddressUpdateApiQuery userAddressUpdateApiQuery) {
        JSONResult result;
        
        if (!(result = checkAddressId(userAddressUpdateApiQuery.getId())).isSuccess() ||
                !(result = checkReceiver(userAddressUpdateApiQuery.getReceiver())).isSuccess() ||
                !(result = checkMobile(userAddressUpdateApiQuery.getMobile())).isSuccess() ||
                !(result = checkAddressInfo(userAddressUpdateApiQuery.getProvince(),
                        userAddressUpdateApiQuery.getCity(),
                        userAddressUpdateApiQuery.getDistrict(),
                        userAddressUpdateApiQuery.getDetail())).isSuccess()
        ) {
            return result;
        }
        
        return JSONResult.success();
    }
    
    private JSONResult checkAddressId(String addressId) {
        if (!StringUtils.hasText(addressId)) {
            return JSONResult.errorUsingMessage("用户地址不能为空");
        }
        
        return JSONResult.success();
    }
    
    private JSONResult checkReceiver(String receiver) {
        if (!StringUtils.hasText(receiver)) {
            return JSONResult.errorUsingMessage("收货人不能为空");
        }
        
        if (receiver.length() > 12) {
            return JSONResult.errorUsingMessage("收货人姓名不能超过12位");
        }
        
        return JSONResult.success();
    }
    
    private JSONResult checkMobile(String mobile) {
        if (!StringUtils.hasText(mobile)) {
            return JSONResult.errorUsingMessage("收货人手机号不能为空");
        }
        
        if (mobile.length() != 11) {
            return JSONResult.errorUsingMessage("收货人手机号长度不正确");
        }
        
        boolean isValid = ValidCheckUtils.checkMobileIsValid(mobile);
        if (!isValid) {
            return JSONResult.errorUsingMessage("收货人手机号格式不正确");
        }
        
        return JSONResult.success();
    }
    
    
    private JSONResult checkAddressInfo(String province, String city, String district, String detail) {
        if (!StringUtils.hasText(province) ||
                !StringUtils.hasText(city) ||
                !StringUtils.hasText(district) ||
                !StringUtils.hasText(detail)) {
            return JSONResult.errorUsingMessage("收货地址信息不能为空");
        }
        
        return JSONResult.success();
    }
}
