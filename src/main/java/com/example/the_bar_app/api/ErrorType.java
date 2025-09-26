package com.example.the_bar_app.api;

import org.springframework.http.HttpStatus;

public enum ErrorType {
    BAD_REQUEST(HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED),
    FORBIDDEN(HttpStatus.FORBIDDEN),
    NOT_FOUND(HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR);

    private final HttpStatus status;
    ErrorType(HttpStatus status) { this.status = status; }
    public HttpStatus status() { return status; }
}
