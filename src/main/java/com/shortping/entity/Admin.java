package com.shortping.entity;

import com.shortping.param.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "admin")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    @Id @Column(name = "admin_id")
    private String adminId;

    @Column(name = "admin_password")
    private String adminPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "admin_role")
    private Role role;
}
