package com.sciatta.openmall.user.mapper.ext;

import com.sciatta.openmall.user.pojo.po.mbg.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created by yangxiaoyu on 2021/7/29<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * UserMapper
 */
public interface UserMapper extends com.sciatta.openmall.user.mapper.mbg.UserMapper {

    User selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    User selectByUsername(@Param("username") String username);
}
