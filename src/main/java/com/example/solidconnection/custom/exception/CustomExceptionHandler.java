package com.example.solidconnection.custom.exception;

import com.example.solidconnection.custom.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ErrorResponse<CustomException> handleCustomException(CustomException e) {
        return new ErrorResponse<>(e);
    }
}
