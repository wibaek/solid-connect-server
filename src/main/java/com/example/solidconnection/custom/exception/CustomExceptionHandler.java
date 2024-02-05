package com.example.solidconnection.custom.exception;

import com.example.solidconnection.custom.response.ErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static com.example.solidconnection.custom.exception.ErrorCode.*;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ex.printStackTrace();
        ErrorResponse errorResponse = new ErrorResponse(ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getCode()));
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex) {
        String errorMessage = ex.getValue() + " 은(는) 유효하지 않은 값입니다.";
        ErrorResponse errorResponse = new ErrorResponse(JSON_PARSING_FAILED, errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(JSON_PARSING_FAILED.getCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach((fieldError) -> {
            errors.add(fieldError.getDefaultMessage());
        });
        ErrorResponse errorResponse = new ErrorResponse(INVALID_INPUT, errors.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(INVALID_INPUT.getCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherException(Exception ex) {
        ex.printStackTrace();
        String errorMessage = ex.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(NOT_DEFINED_ERROR, errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(NOT_DEFINED_ERROR.getCode()));
    }
}