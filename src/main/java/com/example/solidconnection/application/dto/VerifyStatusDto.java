package com.example.solidconnection.application.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerifyStatusDto {
    private String status;
    private int updateCount = 0;

    public VerifyStatusDto(String status){
        this.status = status;
    }
}
