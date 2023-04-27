package com.shortping.dto.cart;

import lombok.Getter;
import lombok.Setter;

public class CartReq {


    @Getter @Setter
    public static class addCart {

        private String email;

        private Long itemNum;

    }





}
