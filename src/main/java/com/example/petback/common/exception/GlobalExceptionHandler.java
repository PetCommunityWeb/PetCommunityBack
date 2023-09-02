package com.example.petback.common.exception;

import com.example.petback.common.advice.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class, NoSuchElementException.class})
    public ResponseEntity illegalExceptionHandler(Exception ex) {
        return ResponseEntity.badRequest().body(new ApiResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }
}
