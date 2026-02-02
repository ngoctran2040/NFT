package com.example.nft.nft_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // ✅ BẮT BUỘC cho MetaMask / cookies / auth
        config.setAllowCredentials(true);

        // ✅ QUAN TRỌNG: dùng allowedOriginPatterns, KHÔNG dùng allowedOrigins("*")
        config.setAllowedOriginPatterns(List.of(
                "http://localhost:3000",
                "http://localhost:5173"
                // sau này thêm domain prod ở đây
        ));

        config.setAllowedHeaders(List.of("*"));
        config.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "DELETE", "OPTIONS"
        ));

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
