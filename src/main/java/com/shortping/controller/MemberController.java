package com.shortping.controller;


import com.shortping.dto.AuthDTO;
import com.shortping.dto.MemberReq;
import com.shortping.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth/general")
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody MemberReq.SignUp signUp) {
        return memberService.signUp(signUp);
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberReq.Login login) {
        return memberService.login(login);
    }
     /**
     * 로그아웃
     */
     @PostMapping("/logout")
    public ResponseEntity<?> logout(Authentication authentication) {
         String memberEmail = ((AuthDTO) authentication.getPrincipal()).getEmail();
         String accessToken = authentication.getCredentials().toString();
         return memberService.logout(memberEmail, accessToken);
     }

    /**
     * 토큰 재발급
     */

    /**
     * 이메일 인증 번호 발송
     */

    /**
     * 이메일 인증
     */

    /**
     * 아이디 찾기
     */

    /**
     * 비밀번호 찾기
     */

    /**
     * 비밀번호 재설정
     */

    /**
     * 회원 정보 수정
     */

    /**
     * 회원 탈퇴
     */









}
