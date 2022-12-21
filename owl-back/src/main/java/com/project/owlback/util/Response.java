package com.project.owlback.util;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * How to use
 * 1. Dependency Injection of Response Class
 * 2. Make Controller's return type 'ResponseEntity<?>'
 * 3. Use response.makeResponse(HttpStatus, message, result)
 */

@Component
public class Response {
    @Getter
    @Builder
    private static class Body {
        private Integer code;
        private HttpStatus httpStatus;
        private String message;
        private Integer count;
        private Object result;
    }

    public ResponseEntity<?> makeResponse(HttpStatus httpStatus, String message, int count, Object result) {
        Body body = Body.builder()
                .code(httpStatus.value())
                .httpStatus(httpStatus)
                .message(message)
                .count(count)
                .result(result)
                .build();

        return ResponseEntity.ok(body);
    }

    public ResponseEntity<?> makeResponse(HttpStatus httpStatus, String message) {
        return makeResponse(httpStatus, message, 0, null);
    }
}