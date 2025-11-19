package org.example.smart_delivery.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private  String secret ;

    // Convert string secret → HMAC-SHA key
    private Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username , String role){
        Map<String,Object> claims = new HashMap<>();
        claims.put("role",role);

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000)) //24H
                .signWith(getSigningKey())
                .compact();
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
