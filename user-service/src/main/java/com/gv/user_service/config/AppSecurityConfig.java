package com.gv.user_service.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
public class AppSecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/swagger-ui/**", "/customers/api/v1/**", "/api/v1/preAuth/**", "/api/v1/postAuth/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    @Bean
    @SneakyThrows
    public SecurityFilterChain filterChainAdditional(HttpSecurity httpSecurity) {
        log.info("[AppSecurityConfig] >> [filterChainAdditional]");
        httpSecurity.authorizeHttpRequests(req -> req
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest()
                        .authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    // Create the bean for PasswordEncoder
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
