package com.sciatta.openmall.api.support.exception;

import com.sciatta.openmall.common.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * Created by yangxiaoyu on 2021/9/12<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 处理@RequestParam上的请求参数异常
     *
     * @param e ConstraintViolationException异常
     * @return JSONResult
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public JSONResult handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining());
        
        log.warn("handleConstraintViolationException::{}", message);
        return JSONResult.errorUsingMessage(message);
    }
    
    /**
     * 处理@RequestBody上的请求参数异常
     *
     * @param e MethodArgumentNotValidException异常
     * @return JSONResult
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JSONResult handleMethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining());
        
        log.warn("handleMethodArgumentNotValidExceptionHandler::{}", message);
        return JSONResult.errorUsingMessage(message);
    }
}
