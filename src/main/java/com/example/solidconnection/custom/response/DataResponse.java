package com.example.solidconnection.custom.response;

import lombok.Getter;

@Getter
public class DataResponse<T> extends CustomResponse {
    private final Boolean success = true;
    private final T data;

    public DataResponse(T data){
        this.data = data;
    }
}
