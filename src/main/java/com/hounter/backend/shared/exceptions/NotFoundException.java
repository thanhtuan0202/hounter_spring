package com.hounter.backend.shared.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends RuntimeException{
        HttpStatus status;
    public NotFoundException(){
        super();
    }
    public NotFoundException(String msg, HttpStatus status){
        super(msg);
        this.status = status;
    }
}
