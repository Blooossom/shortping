package com.shortping.entity;


import com.shortping.param.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder @Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
public class Member {

    @Id @Column(name = "member_email")
    private String memberEmail;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "money")
    private Long money;

    @Column(name = "address")
    private String address;

    @Column(name = "address_detail")
    private String addressDetail;

    @Column(name = "birth")
    private String birth;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "member_delete")
    private boolean memberDelete;

    public void memberDelete() {
        this.memberDelete = true;
    }

}
