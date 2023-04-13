package com.shortping.dto;

import com.shortping.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberRes {


    @Builder @Getter
    @AllArgsConstructor
    public static class TokenInfo {

        private String accessToken;

        private String refreshToken;

        private Long refreshTokenExpirationTime;

        private String name;

        private Long money;

        public TokenInfo(String acsToken, String rfToken, Long rfExTime) {
            this.accessToken = acsToken;
            this.refreshToken = rfToken;
            this.refreshTokenExpirationTime = rfExTime;
        }

        public TokenInfo(String acsToken, String rfToken, Long rfExTime, Member member) {
            this.accessToken = acsToken;
            this.refreshToken = rfToken;
            this.refreshTokenExpirationTime = rfExTime;
            this.name = member.getName();
            this.money = member.getMoney();
        }


    }
}
