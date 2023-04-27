package com.shortping.controller;


import com.shortping.dto.AuthDTO;
import com.shortping.dto.member.MemberReq;
import com.shortping.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
     * 아이디 찾기
     */
    @GetMapping("/find_email")
    public ResponseEntity<?> findEmail(@RequestBody MemberReq.FindEmail findEmail) {
        return memberService.findEmail(findEmail.getName(), findEmail.getPhone());
    }

    /**
     * 비밀번호 찾기 시 비밀번호 재설정
     */
    @PutMapping("/find_password")
    public ResponseEntity<?> findPassword(@RequestBody MemberReq.FindPassword findPassword) {
        return memberService.findPassword(findPassword);
    }


    /**
     * 회원 정보 내 비밀번호 재설정
     */
    @PutMapping("/update_password")
    public ResponseEntity<?> updatePassword(Authentication authentication, @RequestBody MemberReq.UpdatePassword updatePassword) {
        String email = ((AuthDTO) authentication.getPrincipal()).getEmail();
        return memberService.updatePassword(email, updatePassword);
    }

    /**
     * 회원 정보 수정
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateMemberInfo(Authentication authentication, @RequestBody MemberReq.Update update) {
        String memberEmail = ((AuthDTO)authentication.getPrincipal()).getEmail();
        return memberService.updateMember(memberEmail, update);
    }

    /**
     * 회원 탈퇴
     */
    @PutMapping("/drop")
    public ResponseEntity<?> memberDelete(Authentication authentication) {
        String memberEmail = ((AuthDTO)authentication.getPrincipal()).getEmail();
        return memberService.memberDelete(memberEmail);
    }

    /**
     * 이메일 인증 번호 발송
     */
    @PostMapping("/send_number")
    public ResponseEntity<?> sendAuthNumber(@RequestParam("memberEmail")String memberEmail) {
        return memberService.sendAuthNumber(memberEmail);
    }

    /**
     * 이메일 인증
     */
    @PostMapping("/check_number")
    public ResponseEntity<?> checkAuthNumber(@RequestBody MemberReq.AuthMail authMail) {
        return memberService.checkAuthNumber(authMail);
    }






}
