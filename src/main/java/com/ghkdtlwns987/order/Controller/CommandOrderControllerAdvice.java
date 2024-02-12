package com.ghkdtlwns987.order.Controller;

import com.ghkdtlwns987.order.Exception.Class.OutOfStockException;
import com.ghkdtlwns987.order.Exception.Class.ProductIdNotExistsException;
import com.ghkdtlwns987.order.Exception.ClientException;
import com.ghkdtlwns987.order.Exception.Dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CommandOrderControllerAdvice {

    @ExceptionHandler(ClientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDto> handleClientException(Exception e){
        log.error("[BAD_REQUEST] Client Exception", e);
        ErrorResponseDto error = new ErrorResponseDto(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponseDto> handleException(Exception ex) {
        log.error("[INTERNAL_SERVER_ERROR] handleException", ex);
        ErrorResponseDto error = new ErrorResponseDto(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler({
            ProductIdNotExistsException.class,
            OutOfStockException.class
    })
    public ResponseEntity<ErrorResponseDto> handleProductException(Exception ex) {
        log.error("[BAD_REQUEST] handleProductException", ex);
        ErrorResponseDto error = new ErrorResponseDto(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}