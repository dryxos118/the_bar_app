package com.example.the_bar_app.api;

public record ErrorPayload(String type, String message, String location, Object details) {
}
