package com.shortping.config;


import com.shortping.exception.authorization.CustomAuthEntityPoint;
import com.shortping.jwt.JWTFilter;
import com.shortping.jwt.JWTManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {



    private final JWTManager jwtManager;

    @Autowired
    private CustomAuthEntityPoint customAuthEntityPoint;

    private static final String[] PUBLIC_URLS = {
            "/auth/general/signup", "/auth/general/login", "/auth/general/send_number", "/auth/general/check_number",
            "/auth/general/find_email", "/auth/general/find_password", "/auth/general/reset_password", "auth/general/test",
            "/item/**"
    };

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().mvcMatchers(PUBLIC_URLS);

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception {
        return http
                .authorizeRequests()// 다음 리퀘스트에 대한 사용권한 체크
                .mvcMatchers(PUBLIC_URLS).permitAll() // 가입 및 인증 주소는 누구나 접근가능
                .and()
                .authorizeRequests()// 다음 리퀘스트에 대한 사용권한 체크
                .anyRequest().authenticated()// 그외 나머지 요청은 모두 인증된 회원만 접근 가능
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthEntityPoint)     // 토큰없이 접근하는 경우에 대해 Exception 발생
                .and()
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()       // rest api이므로 csrf 보안이 필요없으므로 disable처리
                .httpBasic().disable()  // 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증할것이므로 세션필요없으므로 생성안함.
                .and()
                .addFilterBefore(JWTFilter.of(jwtManager), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));

        configuration.setAllowedHeaders(List.of("authorization", "content-type", "x-auth-token"));

        configuration.setExposedHeaders(Collections.singletonList("X-auth-token"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source  = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
