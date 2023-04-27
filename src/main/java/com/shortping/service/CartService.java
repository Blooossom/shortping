package com.shortping.service;

import com.shortping.dto.Response;
import com.shortping.dto.cart.CartRes;
import com.shortping.entity.Cart;
import com.shortping.entity.Item;
import com.shortping.entity.Member;
import com.shortping.exception.item.ErrorCode;
import com.shortping.exception.item.ItemException;
import com.shortping.exception.member.MemberException;
import com.shortping.repository.CartRepository;
import com.shortping.repository.ItemRepository;
import com.shortping.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepo;

    private final MemberRepository memberRepo;

    private final ItemRepository itemRepo;

    private final Response response;

    @Transactional
    public ResponseEntity<?> addCart(Long itemNum, String email) {
        Item item = itemRepo.findByItemNum(itemNum)
                .orElseThrow(() -> new ItemException(ErrorCode.ITEM_NOT_FOUND));
        Member member = memberRepo
                .findByMemberEmail(email).orElseThrow(() -> new MemberException(com.shortping.exception.member.ErrorCode.NO_EXISTS_MEMBER_INFO));

        try {
              if (!cartRepo.existsByItemAndMember(item, member)) {
                  cartRepo.save(Cart.builder()
                          .member(member)
                          .item(item)
                          .addAt(LocalDateTime.now())
                          .build());

              }
              else {
                  return response.fail(com.shortping.exception.cart.ErrorCode.ALEADY_EXISTS_ITEM_IN_CART.getStatus(),
                          com.shortping.exception.cart.ErrorCode.ALEADY_EXISTS_ITEM_IN_CART.getMessage());
              }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return response.success(new CartRes.addCart(item.getItemName(), LocalDateTime.now()), "장바구니에 추가하였습니다.");

    }
    public ResponseEntity<?> selectCartList(String memberEmail) {
        List<CartRes.CartList> list = new ArrayList<>();
        try {
            Member member = memberRepo.findByMemberEmail(memberEmail).orElseThrow(() -> new MemberException(com.shortping.exception.member.ErrorCode.NO_EXISTS_MEMBER_INFO));
            list = cartRepo.findByMember(member).stream().map(en -> new CartRes.CartList(en)).collect(Collectors.toList());
            return response.success(list, "성공"  );
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return response.fail("실패");

    }

    @Transactional
    public ResponseEntity<?> deleteCart(String memberEmail, Long itemNum) {
        Member member = memberRepo.findByMemberEmail(memberEmail)
                .orElseThrow(() -> new MemberException(com.shortping.exception.member.ErrorCode.NO_EXISTS_MEMBER_INFO));
        Item item = itemRepo.findByItemNum(itemNum)
                .orElseThrow(() -> new ItemException(ErrorCode.ITEM_NOT_FOUND));
        Cart cart = cartRepo.findByMemberAndItem(member, item).orElse(null);

        try {
            cartRepo.delete(cart);
        }catch (Exception e) {
            e.printStackTrace();
            return response.fail("실패");
        }
        return response.success("성공");
    }

}
