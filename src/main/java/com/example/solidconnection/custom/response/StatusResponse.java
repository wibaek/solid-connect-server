package com.example.solidconnection.custom.response;

import lombok.Getter;

@Getter
public class StatusResponse extends CustomResponse {
    private final boolean status;

    public StatusResponse(boolean status) {
        this.status = status;
    }
}
