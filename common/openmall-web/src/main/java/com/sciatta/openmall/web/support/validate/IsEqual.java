package com.sciatta.openmall.web.support.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yangxiaoyu on 2021/9/13<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 比较两个属性的值是否相同
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Constraint(validatedBy = IsEqualValidate.class)
public @interface IsEqual {
    String field() default "";
    
    String compareField() default "";
    
    String message() default "not equal";
    
    // fix: HV000074: com.sciatta.openmall.web.support.validate.IsEqual contains Constraint annotation, but does not contain a groups parameter.
    Class<?>[] groups() default {};
    
    // fix: HV000074
    Class<? extends Payload>[] payload() default {};
}
