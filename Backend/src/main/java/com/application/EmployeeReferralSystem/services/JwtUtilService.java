package com.application.EmployeeReferralSystem.services;

import com.application.EmployeeReferralSystem.models.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtilService {
    private final Key SECRET_KEY = Keys.hmacShaKeyFor(
            "72c1291c2b6391bfcf76fa6fb6ad46ff8c5767d3e14eafb7574111b874d21b26a17c61f5a0a2fc27f7a9c332194c0e2651825958d1b44e325a2e1a590a2133d6"
                    .getBytes());

    public String generateAccessToken(String username,String role){
        return Jwts.builder()
                .setSubject(username)
                .claim("role","ROLE_"+role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2)) // 2 hours
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
    }

    public String generateRefreshToken(String username, String role){
        return Jwts.builder()
                .setSubject(username)
                .claim("role","ROLE_"+role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 2 hours
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // ✅ Corrected username extraction
    }

    public Role extractUserRole(String token) {
        String role = extractClaim(token, claims -> claims.get("role", String.class));
        return Role.valueOf(role.replace("ROLE_",""));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }
}
