package com.revature.ers.utilities.aop;

import com.revature.ers.entities.enums.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Authorize {
    UserRole role() default UserRole.EMPLOYEE;
}
