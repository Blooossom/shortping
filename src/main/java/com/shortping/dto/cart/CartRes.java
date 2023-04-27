package com.shortping.dto.cart;

import com.shortping.entity.Cart;
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

    @Getter @Setter
    public static class CartList {

        private Long id;

        private String itemName;

        private Long itemPrice;

        private LocalDateTime addAt;


        public CartList(Cart cart) {
            this.id = cart.getId();
            this.itemName = cart.getItem().getItemName();
            this.itemPrice = cart.getItem().getItemPrice();
            this.addAt = cart.getAddAt();
        }
    }


}
