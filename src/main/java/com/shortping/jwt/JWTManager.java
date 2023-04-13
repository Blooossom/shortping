package com.shortping.jwt;

import com.shortping.dto.AuthDTO;
import com.shortping.entity.Member;
import com.shortping.entity.Seller;
import com.shortping.exception.authorization.AuthException;
import com.shortping.exception.authorization.ErrorCode;
import com.shortping.service.TokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Component @Slf4j
@RequiredArgsConstructor
public class JWTManager {

    private final JWTProperties jwtProperties;

    private final TokenService tokenService;

    private final RedisTemplate redisTemplate;

    /**
     * 일반 회원 토큰 생성
     */
    //Access 토큰
    public String generateAccessToken(Member member, Long expiredTime) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(now)
                .claim("memberEmail", member.getMemberEmail())
                .claim("role", member.getRole())
                .setExpiration(new Date(now.getTime() + expiredTime))
                .signWith(SignatureAlgorithm.HS256,getKey(jwtProperties.getSecretKey()))
                .compact();
    }

    //Refresh 토큰
    public String generateRefreshToken(Member member, Long expiredTime) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(now)
                .claim("memberEmail", member.getMemberEmail())
                .claim("role", member.getRole())
                .setExpiration(new Date(now.getTime() + expiredTime))
                .signWith(SignatureAlgorithm.HS256, getKey(jwtProperties.getSecretKey()))
                .compact();
    }

    /**
     * 판매자 토큰 생성
     */
    //Access 토큰
    public String generateSellerAccessToken(Seller seller, Long expiredTime) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(now)
                .claim("sellerEmail", seller.getSellerEmail())
                .claim("role", seller.getRole())
                .setExpiration(new Date(now.getTime() + expiredTime))
                .signWith(SignatureAlgorithm.HS256,getKey(jwtProperties.getSecretKey()))
                .compact();
    }

    //Refresh 토큰
    public String generateSellerRefreshToken(Seller seller, Long expiredTime) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(now)
                .claim("sellerEmail", seller.getSellerEmail())
                .claim("role", seller.getRole())
                .setExpiration(new Date(now.getTime() + expiredTime))
                .signWith(SignatureAlgorithm.HS256, getKey(jwtProperties.getSecretKey()))
                .compact();
    }


    /**
     * 헤더 검증
     */
    public Optional<AuthDTO> getInfoOf(String header) {
        if (!headerValidation(header)) {
            throw new AuthException(ErrorCode.INVALID_HEADER);
        }
        try {
            String token = extractToken(header);

            if (!isValid(token)) {
                return Optional.empty();
            }
            Claims claims = extractClaims(token);

            return Optional.of(new AuthDTO(claims));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private boolean headerValidation(String header) {
        return header != null && header.startsWith(jwtProperties.getTokenPrefix());
    }

    public String extractToken(String header) {
        return header.split(" ")[1].trim();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey(jwtProperties.getSecretKey()))
                .build().parseClaimsJws(token).getBody();
    }

    public boolean isValid(String token) {
        try{
            if (redisTemplate.opsForValue().get(token) != null) {
                throw new AuthException(ErrorCode.EXPIRED_REDIS_TOKEN);
            }
            return (extractClaims(token) != null) &&
                    (tokenService.isMemberValid(extractMember(token)));
        } catch (SecurityException | MalformedJwtException err) {
            log.error("Invalid JWT Token", err);
            throw new AuthException(ErrorCode.INVALID_TOKEN);
        } catch (ExpiredJwtException err) {
            log.error("Expired JWT Token", err);
            throw new AuthException(ErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException err) {
            log.error("Unsupported JWT Token", err);
            throw new AuthException(ErrorCode.INVALID_TOKEN);
        } catch (IllegalArgumentException err) {
            log.error("JWT claims string is empty", err);
            throw new AuthException(ErrorCode.EMPTY_CLAMIS);
        }
    }

    public String extractMember(String token) {
        return extractClaims(token).get("memberEmail", String.class);
    }

    public Long expiredTime(String token) {
        return extractClaims(token).getExpiration().getTime();
    }


    private static Key getKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
