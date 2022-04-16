package com.sciatta.openmall.web.support.exception;

import com.sciatta.openmall.pojo.JSONResult;
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
     * @param e ConstraintViolationException
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
     * @param e MethodArgumentNotValidException
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
    
    /**
     * 处理应用运行时异常
     *
     * @param e RuntimeException
     * @return JSONResult
     */
    @ExceptionHandler(RuntimeException.class)
    public JSONResult handleRuntimeException(RuntimeException e) {
        log.warn("handleRuntimeException::{}", e.getMessage());
        return JSONResult.errorRuntimeException(e.getMessage());
    }
}
