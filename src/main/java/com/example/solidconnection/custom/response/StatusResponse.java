package com.example.solidconnection.custom.response;

import lombok.Getter;

@Getter
public class StatusResponse extends CustomResponse {
    private final Boolean status;

    StatusResponse(Boolean status) {
        this.status = status;
    }
}
