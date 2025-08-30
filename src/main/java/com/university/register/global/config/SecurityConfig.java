package com.university.register.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())     // CSRF 비활성화
                .cors(cors -> {})                 // CORS 기본
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()     // ✅ 모든 경로 허용
                )
                .formLogin(form -> form.disable())  // 선택: 폼 로그인 비활성
                .httpBasic(basic -> basic.disable())// 선택: Basic 비활성
                .build();
    }
}
