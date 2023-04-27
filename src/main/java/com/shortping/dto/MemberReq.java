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

    @Getter @Setter
    public static class FindEmail {

        private String name;

        private String phone;

    }

    @Getter @Setter
    public static class Update {

        private String phone;

        private String address;

        private String addressDetail;

    }

    @Getter @Setter
    public static class AuthMail {

        private String memberEmail;

        private String authNumber;

    }

    @Getter @Setter
    public static class UpdatePassword {

        private String originPassword;

        private String newPassword;

        private String checkNewPassword;


    }


}
