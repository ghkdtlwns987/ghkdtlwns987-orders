package com.ghkdtlwns987.order.Controller;

import com.ghkdtlwns987.order.Dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class OrderControllerAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Object>> handleException(Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseDto.builder()
                        .success(false)
                        .status(HttpStatus.BAD_REQUEST)
                        .errorMessages(List.of(e.getMessage()))
                        .build()
        );
    }
}