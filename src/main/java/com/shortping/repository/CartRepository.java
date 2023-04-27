package com.shortping.repository;

import com.shortping.dto.cart.CartRes;
import com.shortping.entity.Cart;
import com.shortping.entity.Item;
import com.shortping.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    boolean existsByItemAndMember(Item item, Member member);

    Optional<Cart> findByMember(Member member);

    Optional<Cart> findByMemberAndItem(Member member, Item item);

    void deleteByItem(Item item);

    //List<CartRes.CartList> findByMember(Member member);

}
