package com.example.demo.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
  private final Key key; private final long expMillis;

  public JwtUtil(@Value("${app.jwt.secret}") String secret,
                 @Value("${app.jwt.exp-minutes}") long expMinutes) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    this.expMillis = expMinutes * 60_000L;
  }

  public String generate(String username){
    long now = System.currentTimeMillis();
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date(now))
        .setExpiration(new Date(now + expMillis))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public String getUsername(String token){
    return Jwts.parserBuilder().setSigningKey(key).build()
        .parseClaimsJws(token).getBody().getSubject();
  }
}

