package com.shortping.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


public class CartRes {

    @Getter @Setter
    @AllArgsConstructor
    public static class addCart {

        private String itemName;

        private LocalDateTime addAt;
    }


}
