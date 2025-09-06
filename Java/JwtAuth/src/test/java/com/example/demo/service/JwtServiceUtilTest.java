package com.example.demo.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Jwt service util test.
 *
 * @author Vivek Jadhav
 */
class JwtServiceUtilTest {

    @InjectMocks
    private JwtServiceUtil jwtServiceUtil;

    private String secret = "0123456789abcdef0123456789abcdef"; // 32 bytes for HS256
    private String username = "user";
    private String password = "pass";
    private String domain = "domain";
    private String groupCode = "group";
    private long expiration = 3600000L;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        setPrivateField(jwtServiceUtil, "secret", secret);
        setPrivateField(jwtServiceUtil, "userName", username);
        setPrivateField(jwtServiceUtil, "password", password);
        setPrivateField(jwtServiceUtil, "domain", domain);
        setPrivateField(jwtServiceUtil, "groupCode", groupCode);
        setPrivateField(jwtServiceUtil, "expiration", expiration);
    }

    private void setPrivateField(Object target, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generate token returns token when credentials are valid.
     */
    @Test
    void generateTokenReturnsTokenWhenCredentialsAreValid() {
        String token = jwtServiceUtil.generateToken(username, password, domain, groupCode);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    /**
     * Generate token throws exception when credentials are invalid.
     */
    @Test
    void generateTokenThrowsExceptionWhenCredentialsAreInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                jwtServiceUtil.generateToken("wrong", password, domain, groupCode));
        assertThrows(IllegalArgumentException.class, () ->
                jwtServiceUtil.generateToken(username, "wrong", domain, groupCode));
        assertThrows(IllegalArgumentException.class, () ->
                jwtServiceUtil.generateToken(username, password, "wrong", groupCode));
        assertThrows(IllegalArgumentException.class, () ->
                jwtServiceUtil.generateToken(username, password, domain, "wrong"));
    }

    /**
     * Gets expiration time returns configured expiration.
     */
    @Test
    void getExpirationTimeReturnsConfiguredExpiration() {
        assertEquals(expiration, jwtServiceUtil.getExpirationTime());
    }

    /**
     * Extract username returns correct username from token.
     */
    @Test
    void extractUsernameReturnsCorrectUsernameFromToken() {
        String token = jwtServiceUtil.generateToken(username, password, domain, groupCode);
        String extracted = jwtServiceUtil.extractUsername(token);
        assertEquals(username, extracted);
    }

    /**
     * Validate token returns true for valid token.
     */
    @Test
    void validateTokenReturnsTrueForValidToken() {
        String token = jwtServiceUtil.generateToken(username, password, domain, groupCode);
        assertTrue(jwtServiceUtil.validateToken(token));
    }

    /**
     * Validate token returns false for invalid token.
     */
    @Test
    void validateTokenReturnsFalseForInvalidToken() {
        String token = jwtServiceUtil.generateToken(username, password, domain, groupCode);
        setPrivateField(jwtServiceUtil, "userName", "other");
        assertFalse(jwtServiceUtil.validateToken(token));
    }

    /**
     * Validate token returns false for expired token.
     */
    @Test
    void validateTokenReturnsFalseForExpiredToken() {
        byte[] keyBytes = secret.getBytes();
        if (keyBytes.length != 32) {
            keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
        }
        String expiredToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis() - 7200000L))
                .setExpiration(new Date(System.currentTimeMillis() - 3600000L))
                .signWith(Keys.hmacShaKeyFor(keyBytes), SignatureAlgorithm.HS256)
                .compact();
        assertFalse(jwtServiceUtil.validateToken(expiredToken));
    }

    /**
     * Gets user details returns user details with correct username.
     */
    @Test
    void getUserDetailsReturnsUserDetailsWithCorrectUsername() {
        UserDetails details = jwtServiceUtil.getUserDetails(username);
        assertEquals(username, details.getUsername());
        assertTrue(details.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER")));
    }
}