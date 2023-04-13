package com.shortping.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class Response<T> {

    @Getter @Builder
    private static class Body {
        private int state;
        private String result;
        private String message;
        private Object data;
    }

    public ResponseEntity<?> success (Object data, String msg, HttpStatus status) {
        Body body = Body.builder()
                .state(status.value())
                .result("SUCCESS")
                .message(msg)
                .data(data)
                .build();
        return ResponseEntity.ok(body);
    }

    public ResponseEntity<?> success(String msg) {
        return success(null, msg, HttpStatus.OK);
    }

    public ResponseEntity<?> success(Object data) {
        return success(data, null, HttpStatus.OK);
    }
    public ResponseEntity<?> success(Object data, String msg) {
        return success(data, msg, HttpStatus.OK);
    }

    public ResponseEntity<?> fail (Object data, String msg, HttpStatus status) {
        Body body = Body.builder()
                .state(status.value())
                .result("FAIL")
                .message(msg)
                .data(data)
                .build();
        return ResponseEntity.internalServerError().body(body);
    }

    public ResponseEntity<?> fail(String msg) {
        return fail(null, msg, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> fail(String msg, HttpStatus status) {
        return fail(null, msg, status);
    }

    public ResponseEntity<?> fail(Object data) {
        return fail(data, null, HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<?> fail(Object data, String msg) {
        return fail(data, msg, HttpStatus.BAD_REQUEST);
    }

}
