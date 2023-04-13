package com.shortping.entity;

import com.shortping.param.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "seller")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Seller {

    @Id @Column(name = "seller_email")
    private String sellerEmail;

    @Column(name = "seller_password")
    private String sellerPassword;

    @Column(name = "seller_name")
    private String sellerName;

    @Column(name = "seller_phone")
    private String sellerPhone;

    @Column(name = "company_no")
    private String companyNo;

    @Column(name = "seller_address")
    private String sellerAddress;

    @Column(name = "seller_address_detail")
    private String sellerAddressDetail;

    @Column(name = "seller_birth")
    private String sellerBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "seller_role")
    private Role role;

    @Column(name = "seller_delete")
    private boolean sellerDelete;

}
