package com.example.solidconnection.custom.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {
    private int code;
    private String message;

    public CustomException(ErrorCode errorCode){
        code = errorCode.getCode();
        message = errorCode.getMessage();
    }

    public CustomException(ErrorCode errorCode, String detail){
        code = errorCode.getCode();
        message = errorCode.getMessage() + detail;
    }
}
