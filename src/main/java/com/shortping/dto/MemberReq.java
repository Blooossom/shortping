package com.shortping.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public class MemberReq {

    @Getter @Setter
    public static class SignUp {

        private String memberEmail;

        private String password;

        private String name;

        private String phone;

        private String address;

        private String addressDetail;

        private String birth;
    }
    @Getter @Setter
    public static class Login {

        private String memberEmail;

        private String password;
    }



}
