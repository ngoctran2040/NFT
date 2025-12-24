package com.example.nft.nft_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {})
            .authorizeHttpRequests(auth -> auth
                // ✅ Cho phép public để load NFT + login metamask
                .requestMatchers(
                    "/api/nfts/**",
                    "/api/users/nonce/**",
                    "/api/users/login"
                ).permitAll()

                // ✅ Tạm thời cho tất cả request qua để test (khỏi 401)
                .anyRequest().permitAll()
            );

        return http.build();
    }
}
