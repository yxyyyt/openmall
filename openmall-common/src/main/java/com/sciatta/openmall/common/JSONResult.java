package com.sciatta.openmall.common;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.sciatta.openmall.common.JSONResult.JSONResultStatus.*;

/**
 * Created by yangxiaoyu on 2021/7/16<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 返回客户端JSON响应
 */
public class JSONResult {
    // 响应业务状态
    private final JSONResultStatus status;
    
    // 响应消息
    private final String message;
    
    // 响应数据
    private final Object data;
    
    private JSONResult() {
        this(null);
    }
    
    private JSONResult(Object data) {
        this(SUCCESS, SUCCESS.name(), data);
    }
    
    private JSONResult(JSONResultStatus status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    
    public static JSONResult success() {
        return success(null);
    }
    
    public static JSONResult success(Object data) {
        return new JSONResult(data);
    }
    
    public static JSONResult errorUsingMessage(String message) {
        return new JSONResult(ERROR_USING_MESSAGE, message, null);
    }
    
    public static JSONResult errorUsingData(Object data) {
        return new JSONResult(ERROR_USING_DATA, ERROR_USING_DATA.name(), data);
    }
    
    public static JSONResult errorToken(String message) {
        return new JSONResult(ERROR_TOKEN, message, null);
    }
    
    public static JSONResult errorRuntimeException(String message) {
        return new JSONResult(ERROR_RUNTIME_EXCEPTION, message, null);
    }
    
    public Boolean isSuccess() {
        return this.status == JSONResultStatus.SUCCESS;
    }
    
    public Integer getStatus() {
        return status.getStatus();
    }
    
    public String getMessage() {
        return message;
    }
    
    public Object getData() {
        return data;
    }
    
    enum JSONResultStatus {
        SUCCESS(200),
        
        ERROR_USING_MESSAGE(500),
        ERROR_USING_DATA(501),
        ERROR_TOKEN(502),
        ERROR_RUNTIME_EXCEPTION(503);
        
        private final Integer status;
        
        JSONResultStatus(Integer status) {
            this.status = status;
        }
        
        public Integer getStatus() {
            return status;
        }
    }
}
