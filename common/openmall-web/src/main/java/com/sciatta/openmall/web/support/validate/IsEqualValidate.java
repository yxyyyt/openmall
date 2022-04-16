package com.sciatta.openmall.web.support.validate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

/**
 * Created by yangxiaoyu on 2021/9/13<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 比较两个属性是否相等
 */
@Slf4j
public class IsEqualValidate implements ConstraintValidator<IsEqual, Object> {
    private String field;
    private String compareField;
    private String message;
    
    @Override
    public void initialize(IsEqual constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.compareField = constraintAnnotation.compareField();
        this.message = constraintAnnotation.message();
    }
    
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (ObjectUtils.isEmpty(o) || !StringUtils.hasText(this.field) || !StringUtils.hasText(this.compareField))
            return false;
        
        Object fieldValue = getFieldValue(o, this.field);
        Object compareFieldValue = getFieldValue(o, this.compareField);
        
        if (ObjectUtils.isEmpty(fieldValue) || ObjectUtils.isEmpty(compareFieldValue)) {
            return false;
        }
        
        return ObjectUtils.nullSafeEquals(fieldValue, compareFieldValue);
    }
    
    private Object getFieldValue(Object o, String fieldName) {
        try {
            Field field = o.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(o);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
