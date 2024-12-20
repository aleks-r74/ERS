package com.revature.ers.controllers.exceptions;

import org.aspectj.weaver.ast.Not;

public class NotAuthorizedException extends RuntimeException{
    public NotAuthorizedException(){}
    public NotAuthorizedException(String message){
        super(message);
    }
}
