package com.gv.user_service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class JWTConfig {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpire;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpire;

    @Value("${jwt.algorithm}")
    private String algorithm;
}
