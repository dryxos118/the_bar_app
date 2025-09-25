package com.example.the_bar_app.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorPayload> handleApp(AppException ex, HttpServletRequest req) {
        if (ex.getLocation() == null || ex.getLocation().isBlank()) {
            ex.setLocation(safeLocation(req));
        }
        log.warn("Warning: {}, Location: {}", ex.getMessage(), ex.getLocation());
        var body = new ErrorPayload(ex.getType().name(), ex.getMessage(), ex.getLocation(), ex.getDetails());
        return ResponseEntity.status(ex.getType().status()).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorPayload> handleOther(Exception ex, HttpServletRequest req) {
        String loc = safeLocation(req);
        log.error("Unhandled error at {}: {}", loc, ex.getMessage(), ex);
        var body = new ErrorPayload(ErrorType.INTERNAL_SERVER_ERROR.name(), "Unexpected error", loc, null);
        return ResponseEntity.status(ErrorType.INTERNAL_SERVER_ERROR.status()).body(body);
    }

    private static String safeLocation(HttpServletRequest req) {
        String uri = req.getRequestURI();
        String qs = req.getQueryString();
        String base = req.getMethod() + " " + uri;
        if (qs == null || qs.isBlank()) return base;
        String masked = qs.replaceAll("(?i)(password|token|access_token|secret)=([^&]+)", "$1=***");
        return base + "?" + masked;
    }
}
