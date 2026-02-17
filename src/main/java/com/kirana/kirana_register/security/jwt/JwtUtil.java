package com.kirana.kirana_register.security.jwt;

import com.kirana.kirana_register.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private static final long ACCESS_TOKEN_VALIDITY_MS = 15 * 60 * 1000;

    // I will move this to application properties later and use a more secure key management strategy
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Generate ACCESS TOKEN
     */
    public String generateAccessToken(UserPrincipal principal) {

        Date now = new Date();
        Date expiry = new Date(now.getTime() + ACCESS_TOKEN_VALIDITY_MS);

        return Jwts.builder()
                .setSubject(principal.getUsername()) // phone number
                .claim("uid", principal.getUserId())
                .claim("role", principal.getRole())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey)
                .compact();
    }

    /**
     * Validate access token signature + expiry
     */
    public boolean validateToken(String token) {
        try {
            getAllClaims(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Extract all claims
     */
    public Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Helpers
     */
    public String getPhoneNumber(String token) {
        return getAllClaims(token).getSubject();
    }

    public String getUserId(String token) {
        return getAllClaims(token).get("uid", String.class);
    }

    public String getRole(String token) {
        return getAllClaims(token).get("role", String.class);
    }
}
