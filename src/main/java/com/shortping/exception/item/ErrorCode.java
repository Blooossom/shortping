package com.shortping.exception.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    ITEM_NOT_FOUND(HttpStatus.BAD_REQUEST, "CAN_NOT_FIND_ITEM");

    private HttpStatus status;

    private String message;


}
