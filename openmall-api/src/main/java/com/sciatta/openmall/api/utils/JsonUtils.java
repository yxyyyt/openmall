package com.sciatta.openmall.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/7/29<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * JsonUtils
 */
public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    /**
     * 将对象转换json字符串
     *
     * @param data 对象
     * @return 转换json字符串
     */
    public static String objectToJson(Object data) {
        try {
            return MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
        return null;
    }
    
    /**
     * 将json字符串转化pojo
     *
     * @param jsonData json字符串
     * @param beanType 待转换对象类型
     * @param <T>      待转换对象类型泛型
     * @return 转化pojo
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            return MAPPER.readValue(jsonData, beanType);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
    
    /**
     * 将json字符串转化list
     *
     * @param jsonData json字符串
     * @param beanType 待转换对象类型
     * @param <T>      待转换对象类型泛型
     * @return 转化list
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            return MAPPER.readValue(jsonData, javaType);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        
        return null;
    }
}
