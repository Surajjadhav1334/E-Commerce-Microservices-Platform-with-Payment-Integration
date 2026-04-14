package com.paymentservice.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	private SecretKey getKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}
	
	 private Key getSigningKey() {
	        return Keys.hmacShaKeyFor(secret.getBytes());
	    }

	public String extractUsername(String token) {
		return extractClaims(token).getSubject();
	}

	public boolean validateToken(String token) {
		try {
			extractClaims(token);
			return true;
		} catch (JwtException e) {
			System.out.println("JWT Error: " + e.getMessage()); // 🔥 add this
			return false;
		}
	}

	private Claims extractClaims(String token) {
	    return Jwts.parserBuilder()
	            .setSigningKey(getKey())   // 🔥 correct key use कर
	            .build()
	            .parseClaimsJws(token)
	            .getBody();
	}
	public String extractRole(String token) {
	    return extractClaims(token).get("role", String.class);
	}
}