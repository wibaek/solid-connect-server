package com.example.solidconnection.custom.response;

import lombok.Getter;

@Getter
public class ErrorResponse<CustomException> extends CustomResponse {
    private final Boolean success = false;
    private final CustomException exception;

    public ErrorResponse(CustomException exception) {
        this.exception = exception;
    }
}
