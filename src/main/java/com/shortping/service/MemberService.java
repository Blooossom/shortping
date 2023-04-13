package com.shortping.service;


import com.shortping.dto.MemberReq;
import com.shortping.dto.Response;
import com.shortping.entity.Member;
import com.shortping.exception.member.ErrorCode;
import com.shortping.exception.member.MemberException;
import com.shortping.param.Role;
import com.shortping.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepo;

    private final Response response;

    private RedisTemplate redisTemplate;

    private final PasswordEncoder encoder;

    public ResponseEntity<?> signUp(MemberReq.SignUp signUp) {
        System.out.println(signUp.getPassword().toString());
        try {
            if (memberRepo.existsByMemberEmail(signUp.getMemberEmail())) {
                return response.fail(
                        String.format("%s : %s", ErrorCode.DUPLICATE_EMAIL.getMessage(), signUp.getMemberEmail()),
                        ErrorCode.DUPLICATE_EMAIL.getStatus());
            }
            Member member = Member.builder()
                    .memberEmail(signUp.getMemberEmail())
                    .password(encoder.encode(signUp.getPassword()))
                    .phone(signUp.getPhone())
                    .name(signUp.getName())
                    .address(signUp.getAddress())
                    .addressDetail(signUp.getAddressDetail())
                    .money(Long.valueOf(0))
                    .birth(signUp.getBirth())
                    .role(Role.ROLE_BUYER)
                    .memberDelete(false)
                    .build();
            memberRepo.save(member);
            return response.success("회원가입에 성공하셨습니다.");
        }
        catch (MemberException e) {
            e.printStackTrace();
            throw new MemberException(ErrorCode.SIGNUP_FAILED);
        }
    }

}
