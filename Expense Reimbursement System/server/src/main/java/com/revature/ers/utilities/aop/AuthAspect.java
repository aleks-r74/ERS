package com.revature.ers.utilities.aop;

import com.revature.ers.controllers.exceptions.NotAuthorizedException;
import com.revature.ers.entities.enums.UserRole;
import com.revature.ers.security.AuthContext;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@AllArgsConstructor
public class AuthAspect {
    private AuthContext authContext;

    @Before("@annotation(Authenticate)")
    @Order(1)
    public void checkAuthentication(){
        if(authContext.getUsername() == null)
            throw new NotAuthorizedException("You're not authenticated");
    }

    @Before("@annotation(Authorize)")
    @Order(2)
    public void checkAuthorization(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        UserRole role = method.getAnnotation(Authorize.class).role();
        if(authContext.getRole().ordinal() < role.ordinal())
            throw new NotAuthorizedException("You're not authorized");
    }

}
