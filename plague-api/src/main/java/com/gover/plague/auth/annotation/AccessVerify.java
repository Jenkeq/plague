package com.gover.plague.auth.annotation;

import com.gover.plague.constant.AccessType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.gover.plague.constant.AccessType.GATEWAY;

@Target(ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface AccessVerify {

    boolean needLogin() default true;

    AccessType accessType() default GATEWAY;

}
