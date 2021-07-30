package com.sciatta.openmall.dao.mapper.ext;

import com.sciatta.openmall.dao.pojo.po.mbg.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created by yangxiaoyu on 2021/7/29<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * UserMapper
 */
public interface UserMapper extends com.sciatta.openmall.dao.mapper.mbg.UserMapper {
    User selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
