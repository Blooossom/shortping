package com.shortping.controller;


import com.shortping.dto.MemberReq;
import com.shortping.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(MemberReq.SignUp signUp) {
        return memberService.signUp(signUp);
    }

    /**
     * 로그인
     */

    /**
     * 로그아웃
     */

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
