package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.config.OpenMallConfig;
import com.sciatta.openmall.api.converter.UserConverter;
import com.sciatta.openmall.api.pojo.query.UserApiQuery;
import com.sciatta.openmall.api.pojo.vo.UserCookieVO;
import com.sciatta.openmall.api.pojo.vo.UserVO;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.common.constants.CookieConstants;
import com.sciatta.openmall.common.constants.RedisCacheConstants;
import com.sciatta.openmall.common.utils.CookieUtils;
import com.sciatta.openmall.common.utils.DateUtils;
import com.sciatta.openmall.common.utils.JsonUtils;
import com.sciatta.openmall.common.utils.SidUtils;
import com.sciatta.openmall.service.UserService;
import com.sciatta.openmall.service.pojo.dto.UserDTO;
import com.sciatta.openmall.service.pojo.query.UserServiceQuery;
import com.sciatta.openmall.service.support.cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;

import static com.sciatta.openmall.common.constants.CookieConstants.COOKIE_USERNAME;

/**
 * Created by yangxiaoyu on 2021/8/13<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 用户信息
 */
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {
    private final UserService userService;
    private final OpenMallConfig openMallConfig;
    private final CacheService cacheService;
    
    public UserController(UserService userService, OpenMallConfig openMallConfig, CacheService cacheService) {
        this.userService = userService;
        this.openMallConfig = openMallConfig;
        this.cacheService = cacheService;
    }
    
    @GetMapping("query")
    public JSONResult query(@RequestParam String userId) {
        UserDTO userDTO = userService.queryUserByUserId(userId);
        UserVO userVO = UserConverter.INSTANCE.userDTOToUserVO(userDTO);
        
        return JSONResult.success(userVO);
    }
    
    @PostMapping("update")
    public JSONResult update(@RequestParam String userId,
                             @RequestBody @Valid UserApiQuery userApiQuery,
                             BindingResult bindingResult,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        
        if (bindingResult.hasErrors()) {
            return JSONResult.errorUsingData(bindingResult);
        }
        
        UserServiceQuery userServiceQuery = UserConverter.INSTANCE.userApiQueryToUserServiceQuery(userApiQuery);
        boolean result = userService.updateUserByUserId(userId, userServiceQuery);
        if (!result) {
            return JSONResult.errorUsingMessage("用户更新失败，请尝试重试");
        }
        
        UserDTO userDTO = userService.queryUserByUserId(userId);
        UserCookieVO userCookieVO = UserConverter.INSTANCE.userDTOToUserCookieVO(userDTO);
        
        // 设置缓存
        setUserCache(userCookieVO, request, response);
        
        return JSONResult.success();
    }
    
    @PostMapping("uploadFace")
    public JSONResult uploadFace(@RequestParam String userId, MultipartFile file,
                                 HttpServletRequest request, HttpServletResponse response) {
        
        FilePath filePath;
        InputStream in = null;
        OutputStream out = null;
        
        if (ObjectUtils.isEmpty(file)) {
            return JSONResult.errorUsingMessage("文件不能为空");
        }
        
        try {
            filePath = new FilePath.Builder()
                    .setFileName(file.getOriginalFilename())
                    .setUserId(userId)
                    .setOpenMallConfig(this.openMallConfig)
                    .build();
        } catch (FilePath.FileBuildException e) {
            return JSONResult.errorUsingMessage(e.getMessage());
        }
        
        // 文件输出到本地目录
        try {
            in = file.getInputStream();
            out = new FileOutputStream(filePath.getLocalFilePath());
            FileCopyUtils.copy(in, out);
        } catch (IOException e) {
            log.error(e.getMessage());
            return JSONResult.errorUsingMessage("上传图片失败");
        } finally {
            try {
                if (!ObjectUtils.isEmpty(in)) {
                    in.close();
                }
                
                if (!(ObjectUtils.isEmpty(out))) {
                    out.close();
                }
            } catch (IOException ignored) {
            }
        }
        
        // 更新用户数据
        UserServiceQuery userServiceQuery = UserConverter.INSTANCE.userFaceUrlToUserServiceQuery(filePath.getServerFilePath());
        if (!userService.updateUserByUserId(userId, userServiceQuery)) {
            return JSONResult.errorUsingMessage("用户更新失败，请尝试重试");
        }
        
        UserDTO userDTO = userService.queryUserByUserId(userId);
        UserCookieVO userCookieVO = UserConverter.INSTANCE.userDTOToUserCookieVO(userDTO);
        
        // 设置缓存
        setUserCache(userCookieVO, request, response);
        
        return JSONResult.success();
    }
    
    private void setUserCache(UserCookieVO userCookieVO, HttpServletRequest request, HttpServletResponse response) {
        String userTokenKey = getUserTokenKey(userCookieVO.getId());
        String userTokenValue = getUserTokenValue();
        
        userCookieVO.setUserUniqueToken(userTokenValue);
        
        // 设置缓存
        cacheService.set(userTokenKey, userTokenValue);
        CookieUtils.setCookie(request, response, COOKIE_USERNAME, JsonUtils.objectToJson(userCookieVO), true);
    }
    
    private String getUserTokenKey(String userId) {
        return RedisCacheConstants.USER_TOKEN + ":" + userId;
    }
    
    private String getUserTokenValue() {
        return SidUtils.generateUUID();
    }
    
    private static class FilePath {
        private final String localFilePath;
        private final String serverFilePath;
        
        private FilePath(String filePath, OpenMallConfig openMallConfig) {
            
            this.localFilePath = openMallConfig.getUpload().getImageUserFaceLocation() + filePath;
            this.serverFilePath = openMallConfig.getUpload().getImageServerUrl() + filePath;
            
            createLocalDirectory();
        }
        
        private void createLocalDirectory() {
            File file = new File(this.localFilePath);
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                boolean test = parentDir.mkdirs();
                log.debug("local directory {} not exit, create and result is {}", parentDir.getPath(), test);
            }
        }
        
        public String getLocalFilePath() {
            return localFilePath;
        }
        
        public String getServerFilePath() {
            // 由于浏览器可能存在缓存的情况，加上时间戳保证更新后的图片可以被及时刷新
            return serverFilePath + "?t=" + DateUtils.getCurrentDateString(DateUtils.DATE_PATTERN);
        }
        
        static class Builder {
            private String fileName;
            private String userId;
            private OpenMallConfig openMallConfig;
            
            public Builder setUserId(String userId) {
                this.userId = userId;
                return this;
            }
            
            public Builder setFileName(String fileName) {
                this.fileName = fileName;
                return this;
            }
            
            public Builder setOpenMallConfig(OpenMallConfig openMallConfig) {
                this.openMallConfig = openMallConfig;
                return this;
            }
            
            public FilePath build() throws FileBuildException {
                if (ObjectUtils.isEmpty(openMallConfig)) {
                    throw new FileBuildException("OpenMallConfig不能为空");
                }
                
                if (!StringUtils.hasText(userId)) {
                    throw new FileBuildException("用户不能为空");
                }
                
                if (!StringUtils.hasText(fileName)) {
                    throw new FileBuildException("文件名不能为空");
                }
                
                String renameFileName = renameFileName();
                
                return new FilePath(makeFilePath(renameFileName), openMallConfig);
            }
            
            private String renameFileName() throws FileBuildException {
                String[] fileNameArr = fileName.split("\\.");
                
                // 获取文件的后缀名
                String suffix = fileNameArr[fileNameArr.length - 1];
                
                if (!suffix.equalsIgnoreCase("png") &&
                        !suffix.equalsIgnoreCase("jpg") &&
                        !suffix.equalsIgnoreCase("jpeg")) {
                    throw new FileBuildException("图片格式不正确，仅支持png|jpg|jpeg");
                }
                
                // face-{userid}.png
                return "face-" + userId + "." + suffix;
            }
            
            private String makeFilePath(String fileName) {
                return File.separator + userId + File.separator + fileName;
            }
        }
        
        private static class FileBuildException extends Exception {
            
            private static final long serialVersionUID = 3928791272247284435L;
            
            public FileBuildException(String message) {
                super(message);
            }
        }
    }
}