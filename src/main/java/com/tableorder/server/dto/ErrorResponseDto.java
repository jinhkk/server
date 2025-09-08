package com.tableorder.server.dto;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponseDto {
    private int status;
    private String message;

    public ErrorResponseDto(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }
}
