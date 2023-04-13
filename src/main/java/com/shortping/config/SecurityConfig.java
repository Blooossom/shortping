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
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
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
            "/auth/**"
    };

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().mvcMatchers(PUBLIC_URLS);

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception {
        return http
                .authorizeRequests()
                .mvcMatchers(PUBLIC_URLS).permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthEntityPoint)
                .and()
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
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

}
