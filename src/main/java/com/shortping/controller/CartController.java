package com.shortping.controller;

import com.shortping.dto.AuthDTO;
import com.shortping.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * 장바구니 등록
     */
    @PostMapping("/add")
    public ResponseEntity<?> addCart(@RequestParam("itemNo") String itemNo, Authentication authentication){

        String memberEmail = ((AuthDTO) authentication.getPrincipal()).getEmail();



        return cartService.addCart(Long.valueOf(itemNo), memberEmail);
    }


    /**
     * 장바구니 삭제
     */
    @DeleteMapping
    public ResponseEntity<?> deleteCart(@RequestParam("itemNo") String itemNo, Authentication authentication) {
        return null;
    }





}
