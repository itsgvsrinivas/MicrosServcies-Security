package com.gv.user_service.security;


import com.gv.user_service.model.User;
import com.gv.user_service.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {

    private final TokenRepository tokenRepository;
    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpire;
    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpire;

    public JwtService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String extractUsername(String token) {
        log.info("[JwtService] >> [extractUsername]");
        return extractClaim(token, Claims::getSubject);
    }


    public boolean isValid(String token, UserDetails user) {
        log.info("[JwtService] >> [isValid]");
        String username = extractUsername(token);

        boolean validToken = tokenRepository
                .findByAccessToken(token)
                .map(t -> !t.isLoggedOut())
                .orElse(false);

        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
    }

    public boolean isValidRefreshToken(String token, User user) {
        log.info("[JwtService] >> [isValidRefreshToken]");
        String username = extractUsername(token);

        boolean validRefreshToken = tokenRepository
                .findByRefreshToken(token)
                .map(t -> !t.isLoggedOut())
                .orElse(false);

        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validRefreshToken;
    }

    private boolean isTokenExpired(String token) {
        log.info("[JwtService] >> [isTokenExpired]");
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        log.info("[JwtService] >> [extractExpiration]");
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        log.info("[JwtService] >> [extractClaim]");
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        log.info("[JwtService] >> [extractAllClaims]");
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public String generateAccessToken(User user) {
        log.info("[JwtService] >> [generateAccessToken]");
        return generateToken(user, accessTokenExpire);
    }

    public String generateRefreshToken(User user) {
        log.info("[JwtService] >> [generateRefreshToken]");
        return generateToken(user, refreshTokenExpire);
    }

    private String generateToken(User user, long expireTime) {
        log.info("[JwtService] >> [generateToken]");
        String token = Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigninKey())
                .compact();

        return token;
    }

    private SecretKey getSigninKey() {
        log.info("[JwtService] >> [getSigninKey]");
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}