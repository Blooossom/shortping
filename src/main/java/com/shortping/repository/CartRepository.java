package com.shortping.repository;

import com.shortping.entity.Cart;
import com.shortping.entity.Item;
import com.shortping.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    boolean existsByItemAndMember(Item item, Member member);

}
