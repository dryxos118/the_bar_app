package com.example.the_bar_app.api;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AppException extends RuntimeException {
    private final ErrorType type;
    @Setter
    private String location;
    private final Object details;

    public AppException(ErrorType type, String message) {
        this(type, message, null, null);
    }
    public AppException(ErrorType type, String message, Object details) {
        this(type, message, null, details);
    }
    public AppException(ErrorType type, String message, String location, Object details) {
        super(message);
        this.type = type; this.location = location; this.details = details;
    }

    public AppException(ErrorType type, Object details) {
        this.type = type;
        this.details = details;
    }

}
