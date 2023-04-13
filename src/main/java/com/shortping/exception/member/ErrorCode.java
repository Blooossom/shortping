package com.shortping.exception.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "Already exist email");

    private HttpStatus status;
    private String message;




}
