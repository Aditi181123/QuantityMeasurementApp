package com.qm.auth.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Component  // Spring will auto-detect and register as bean
public class JwtUtil {

    
    @Value("${jwt.secret}")
    private String secret;

     
    @Value("${jwt.expiration}")
    private Long expiration;

     
    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
 
    public String generateToken(String username, String userId) {
        Date now = new Date();  // Current timestamp
        Date expiryDate = new Date(now.getTime() + expiration);  // +24 hours

     
        return Jwts.builder()
                .setSubject(username)                    // "sub" claim
                .claim("userId", userId)                // Custom claim for user ID
                .setIssuedAt(now)                        // "iat" claim
                .setExpiration(expiryDate)               // "exp" claim
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)  // Sign with HS256
                .compact();                              // Produce: "xxxxx.yyyyy.zzzzz"
    }

   
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())  // Provide key for verification
                .build()
                .parseClaimsJws(token)           // Parse and verify signature
                .getBody();                      // Get payload (claims)
        
        return claims.getSubject();              // Return "sub" claim
    }
 
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        
        return claims.get("userId", String.class);  // Get custom "userId" claim
    }
 
    public boolean validateToken(String token) {
        try {
            
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);  // Throws exception if invalid
            
            return true;  // If we get here, token is valid
        } catch (JwtException | IllegalArgumentException e) {
             
            return false;
        }
    }
}
