package com.devops.reto.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        jwtService.setSecret("ZGV2b3BzLXJldG8tc2VjcmV0LWtleS0yMDI0LXN1cGVyLXNlY3VyZQ==");
    }

    @Test
    void shouldGenerateNonNullToken() {
        String token = jwtService.generateToken();
        assertNotNull(token);
    }

    @Test
    void shouldGenerateUniqueTokensPerTransaction() {
        String token1 = jwtService.generateToken();
        String token2 = jwtService.generateToken();
        assertNotEquals(token1, token2);
    }

    @Test
    void shouldGenerateValidJwtStructure() {
        String token = jwtService.generateToken();
        String[] parts = token.split("\\.");
        assertEquals(3, parts.length);
    }
}
