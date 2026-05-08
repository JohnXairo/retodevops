package com.devops.reto.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Service
@Setter
public class JwtService {

    private static final long TOKEN_EXPIRATION_MS = 60000L;

    @Value("${app.security.jwt-secret}")
    private String secret;

    public String generateToken() {
        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_MS))
                .signWith(key)
                .compact();
    }
}
