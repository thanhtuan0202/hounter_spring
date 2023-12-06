package com.hounter.backend.shared.exceptions;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends RuntimeException{
    HttpStatus status;
    public ForbiddenException(){
        super();
    }
    public ForbiddenException(String msg, HttpStatus status){
        super(msg);
        this.status = status;
    }
}
