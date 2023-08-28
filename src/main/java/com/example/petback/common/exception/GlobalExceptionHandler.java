package com.example.petback.common.exception;

import com.example.petback.common.advice.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity illegalExceptionHandler(Exception ex) {
        return ResponseEntity.ok().body(new ApiResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }
}
