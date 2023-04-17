package com.shortping.service;


import com.shortping.dto.MemberReq;
import com.shortping.dto.MemberRes;
import com.shortping.dto.Response;
import com.shortping.entity.Member;
import com.shortping.exception.member.ErrorCode;
import com.shortping.exception.member.MemberException;
import com.shortping.jwt.JWTManager;
import com.shortping.jwt.JWTProperties;
import com.shortping.param.Role;
import com.shortping.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepo;

    private final Response response;

    private final RedisTemplate redisTemplate;

    private final PasswordEncoder encoder;

    private final JWTManager manager;

    private final JWTProperties properties;
    public ResponseEntity<?> signUp(MemberReq.SignUp signUp) {
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

    public ResponseEntity<?> login(MemberReq.Login login) {
        try {
            Member member = memberRepo.findByMemberEmail(login.getMemberEmail())
                    .orElseThrow(() -> new MemberException(ErrorCode.NO_EXISTS_MEMBER_INFO));

            if (!authPassword(login.getPassword(), member.getPassword())) {
                return response.fail(
                        ErrorCode.INCORRECT_PASSWORD.getMessage(),
                        ErrorCode.INCORRECT_PASSWORD.getStatus()
                );
            }
            MemberRes.TokenInfo loginInfo = new MemberRes.TokenInfo(
                    manager.generateAccessToken(member, properties.getAccessTokenExpiredTime()),
                    manager.generateRefreshToken(member, properties.getRefreshTokenExpiredTime()),
                    properties.getRefreshTokenExpiredTime(),
                    member
            );
            redisTemplate.opsForValue()
                    .set("RT : " + login.getMemberEmail(),
                            loginInfo.getRefreshToken(),
                            properties.getRefreshTokenExpiredTime(),
                            TimeUnit.MILLISECONDS);
            loginRedis(member.getMemberEmail(), loginInfo);

            return response.success(
                    loginInfo,
                    "로그인 성공"
            );
        }
        catch (MemberException e) {
            e.printStackTrace();
            throw new MemberException(ErrorCode.LOGIN_FAILED);
        }
    }

    public boolean authPassword(String inputPassword, String originPassword) {
        return encoder.matches(inputPassword, originPassword);
    }
    public void loginRedis(String email, MemberRes.TokenInfo tokenInfo) {
        try {
            System.out.println(email);
            redisTemplate.opsForValue()
                    .set("RT : " + email,
                            tokenInfo.getRefreshToken(),
                            properties.getRefreshTokenExpiredTime(),
                            TimeUnit.MILLISECONDS);

            if (redisTemplate.opsForValue().get("RT : " + email) == null) {
                throw new MemberException(ErrorCode.REDIS_VERIFY_FAILED);
            }
        }
        catch (MemberException e) {
            e.printStackTrace();
            throw new MemberException(ErrorCode.REDIS_LOGIN_FAILED);
        }
    }

    public ResponseEntity<?> logout(String email, String accessToken) {
        try {
            redisTemplate.delete("RT : " + email);
            System.out.println(email);
            Long expireTime = manager.getExpiredTime(accessToken);

            redisTemplate.opsForValue()
                    .set(accessToken, "logout", expireTime, TimeUnit.MILLISECONDS);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return response.success("로그아웃 되었습니다.");
    }



}
