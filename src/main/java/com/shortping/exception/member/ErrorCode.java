package com.shortping.exception.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "Already exist email"),

    NO_EXISTS_MEMBER_INFO(HttpStatus.BAD_REQUEST, "Member is not exist"),

    INCORRECT_PASSWORD(HttpStatus.BAD_REQUEST, "Password is incorrect"),

    REDIS_VERIFY_FAILED(HttpStatus.BAD_REQUEST, "REDIS Verified failed"),

    REDIS_LOGIN_FAILED(HttpStatus.BAD_REQUEST, "Login failed with redis"),
    SIGNUP_FAILED(HttpStatus.BAD_REQUEST, "Signup Failed"),

    NOT_EQUALS_NEW_PASSWORD_CHECK(HttpStatus.BAD_REQUEST, "Both Password are not equal."),
    NUMBER_GENERATED_FAILED(HttpStatus.BAD_REQUEST, "Number Generated Failed"),
    DROP_FAILED(HttpStatus.BAD_REQUEST, "Member Drop Failed"),
    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "Login Failed");

    private HttpStatus status;
    private String message;




}
