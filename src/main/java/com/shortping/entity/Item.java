package com.shortping.entity;

import com.shortping.param.ItemCategories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Table(name = "item")
@Builder @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id @Column(name = "item_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemNum;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_price")
    private Long itemPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_categories")
    private ItemCategories itemCategories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_email")
    private Seller seller;


}
