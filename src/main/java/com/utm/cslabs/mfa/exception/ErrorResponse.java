package com.utm.cslabs.mfa.exception;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ErrorResponse {
    LocalDateTime timeStamp = LocalDateTime.now();
    int status;
    String error;
    String message;
    String path;

    public ErrorResponse(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}