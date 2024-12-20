package com.revature.ers.controllers;

import com.revature.ers.controllers.dtos.ErrorDTO;
import com.revature.ers.controllers.exceptions.NotAuthorizedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<ErrorDTO> handleIllegalArgumentException(IllegalArgumentException e){
        return ResponseEntity.badRequest().body(new ErrorDTO(e.getMessage()));
    }
    @ExceptionHandler(NotAuthorizedException.class)
    ResponseEntity<ErrorDTO> handleNotAuthorizedException(NotAuthorizedException e){
        return ResponseEntity.status(401).body(new ErrorDTO(e.getMessage()));
    }
}
