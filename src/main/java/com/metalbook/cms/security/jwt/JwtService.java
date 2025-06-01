package com.metalbook.cms.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {
    @Value("${spring.jwt.secretKey}")
    private String jwtSecretKey;

    @Value("${spring.jwt.expirationInMs}")
    private long jwtExpirationInMs;

    public String generateTokenFromUserDetails(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plusMillis(jwtExpirationInMs)))
                .signWith(getJwtSecretKey())
                .compact();
    }

    public String extractUsernameFromToken(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) getJwtSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }


    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token){
        Date expiration = Jwts
                .parser()
                .verifyWith((SecretKey) getJwtSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload().getExpiration();
        return expiration.before(new Date());
    }

    public Key getJwtSecretKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecretKey));
    }
}
