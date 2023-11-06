package com.example.festival.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private String message;

    private String httpStatus = HttpStatus.NOT_FOUND.toString();

    public NotFoundException(String message) {
        this.message = message;
    }
    
}
