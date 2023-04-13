package com.shortping.jwt;


import com.shortping.dto.AuthDTO;
import com.shortping.exception.authorization.AuthException;
import com.shortping.exception.authorization.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component @Getter
public class JWTFilter extends OncePerRequestFilter {

    private final JWTManager jwtManager;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    @Builder
    private JWTFilter(JWTManager jwtManager) {
        this.jwtManager = jwtManager;
    }

    public static JWTFilter of(JWTManager jwtManager) {
        return new JWTFilter(jwtManager);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        try {
            AuthDTO user = jwtManager.getInfoOf(header).orElseThrow(
                    () -> new AuthException(ErrorCode.INVALID_TOKEN));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user,
                    jwtManager.extractToken(header),
                    user.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }
        catch (RuntimeException e) {
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);

    }
}
