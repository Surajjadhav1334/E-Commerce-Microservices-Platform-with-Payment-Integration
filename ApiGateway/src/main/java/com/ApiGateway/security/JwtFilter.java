package com.ApiGateway.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.*;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(-1)
public class JwtFilter implements GlobalFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        if (path.contains("/auth/login") || path.contains("/auth/register")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing Token");
        }

        String token = authHeader.substring(7);

        String username = jwtUtil.getUsername(token);
        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Invalid Token");
        }
     
        String role = jwtUtil.getRole(token);
        
        System.out.println("Token: " + token);
        System.out.println("Username: " + username);
        System.out.println("Role: " + role);
        
        System.out.println("Path: " + path);
        System.out.println("Role from token: " + role);

        // ADMIN only API
        if (path.contains("/products") && !"ROLE_ADMIN".equals(role)) {
            throw new RuntimeException("Only ADMIN can manage products");
        }
        
        if (path.contains("/orders") && "ROLE_ADMIN".equals(role)) {
            throw new RuntimeException("Admin cannot place orders");
        }

        // USER only API
        if (path.contains("/orders") && !"ROLE_USER".equals(role)) {
            throw new RuntimeException("Only USER can place orders");
        }
        
//        UsernamePasswordAuthenticationToken auth =
//                new UsernamePasswordAuthenticationToken(
//                        username,
//                        null,
//                        List.of(new SimpleGrantedAuthority(role)) // ROLE_ADMIN / ROLE_USER
//                );

        return chain.filter(exchange);
    }
  }
