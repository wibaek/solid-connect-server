package com.example.solidconnection.custom.response;

import com.example.solidconnection.custom.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse extends CustomResponse {
    private final Boolean success = false;
    private ErrorDetail error;

    @Getter
    @AllArgsConstructor
    private static class ErrorDetail {
        int code;
        String message;
    }

    public ErrorResponse(CustomException e) {
        this.error = new ErrorDetail(e.getCode(), e.getMessage());
    }
}
