package com.example.solidconnection.custom.exception;

import com.example.solidconnection.custom.response.ErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.example.solidconnection.custom.exception.ErrorCode.JSON_PARSING_FAILED;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorResponse errorResponse = new ErrorResponse(e);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getCode()));
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex) {
        String errorMessage = ex.getValue() + " 은(는) 유효하지 않은 값입니다.";
        ErrorResponse errorResponse = new ErrorResponse(JSON_PARSING_FAILED, errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(JSON_PARSING_FAILED.getCode()));
    }
}