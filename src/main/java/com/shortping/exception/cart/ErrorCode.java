package com.shortping.exception.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {


    ALEADY_EXISTS_ITEM_IN_CART(HttpStatus.BAD_REQUEST, "Item already exists in cart.");

    private HttpStatus status;
    private String message;

}
