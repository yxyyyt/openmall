package com.sciatta.openmall.user.web.controller;

import com.sciatta.openmall.common.utils.DateUtils;
import com.sciatta.openmall.pojo.JSONResult;
import com.sciatta.openmall.user.api.UserService;
import com.sciatta.openmall.user.pojo.dto.UserDTO;
import com.sciatta.openmall.user.pojo.query.UserQuery;
import com.sciatta.openmall.user.pojo.vo.UserCookieVO;
import com.sciatta.openmall.user.pojo.vo.UserVO;
import com.sciatta.openmall.user.web.converter.UserConverter;
import com.sciatta.openmall.user.web.support.UserCacheHelper;
import com.sciatta.openmall.web.config.OpenMallConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.*;

/**
 * Created by yangxiaoyu on 2021/8/13<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 用户信息
 */
@Validated
@RestController
@RequestMapping("user")
@Slf4j
public class MyInfoController {
    private final UserService userService;
    private final OpenMallConfig openMallConfig;
    private final UserCacheHelper userCacheHelper;

    public MyInfoController(UserService userService, OpenMallConfig openMallConfig, UserCacheHelper userCacheHelper) {
        this.userService = userService;
        this.openMallConfig = openMallConfig;
        this.userCacheHelper = userCacheHelper;
    }

    @GetMapping("query")
    public JSONResult query(@RequestParam @NotBlank(message = "用户标识不能为空") String userId) {
        UserDTO userDTO = userService.queryUserByUserId(userId);
        if (ObjectUtils.isEmpty(userDTO)) {
            return JSONResult.errorUsingMessage("用户标识不存在");
        }

        UserVO userVO = UserConverter.INSTANCE.toUserVO(userDTO);

        return JSONResult.success(userVO);
    }

    @PostMapping("update")
    public JSONResult update(
            @RequestParam @NotBlank(message = "用户标识不能为空") String userId,
            @RequestBody @Validated(UserQuery.WebUpdate.class) UserQuery userQuery,
            HttpServletRequest request,
            HttpServletResponse response) {

        if (ObjectUtils.isEmpty(userService.queryUserByUserId(userId))) {
            return JSONResult.errorUsingMessage("用户标识不存在");
        }

        boolean result = userService.updateUserByUserId(userId, userQuery);
        if (!result) {
            return JSONResult.errorUsingMessage("用户更新失败，请尝试重试");
        }

        UserDTO userDTO = userService.queryUserByUserId(userId);
        UserCookieVO userCookieVO = UserConverter.INSTANCE.toUserCookieVO(userDTO);

        // 设置缓存
        userCacheHelper.syncUserCache(userCookieVO, request, response);

        return JSONResult.success();
    }

    @PostMapping("uploadFace")
    public JSONResult uploadFace(
            @RequestParam @NotBlank(message = "用户标识不能为空") String userId,
            @NotNull(message = "文件不能为空") MultipartFile file,
            HttpServletRequest request,
            HttpServletResponse response) {

        FilePath filePath;
        InputStream in = null;
        OutputStream out = null;

        if (ObjectUtils.isEmpty(userService.queryUserByUserId(userId))) {
            return JSONResult.errorUsingMessage("用户标识不存在");
        }

        try {
            filePath = new FilePath.Builder().setFileName(file.getOriginalFilename()).setUserId(userId).setOpenMallConfig(this.openMallConfig).build();
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
        if (!userService.updateUserByUserId(userId,
                UserConverter.INSTANCE.toUserQuery(filePath.getServerFilePath()))) {
            return JSONResult.errorUsingMessage("用户更新失败，请尝试重试");
        }

        UserDTO userDTO = userService.queryUserByUserId(userId);
        UserCookieVO userCookieVO = UserConverter.INSTANCE.toUserCookieVO(userDTO);

        // 设置缓存
        userCacheHelper.sync(userCookieVO, request, response);

        return JSONResult.success();
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
                    throw new FileBuildException("用户标识不能为空");
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

                if (!suffix.equalsIgnoreCase("png") && !suffix.equalsIgnoreCase("jpg") && !suffix.equalsIgnoreCase("jpeg")) {
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