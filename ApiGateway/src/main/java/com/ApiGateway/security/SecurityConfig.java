package com.ApiGateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

    	 http
         .csrf(csrf -> csrf.disable())
         .authorizeExchange(exchange -> exchange
             // Allow auth APIs
             .pathMatchers("/auth/**").permitAll()
             
//             .pathMatchers("/products/**").hasRole("ADMIN")
//
//             // 👤 USER ONLY
//             .pathMatchers("/orders/**").hasRole("USER")

             // बाकी सर्व allow (Gateway filter handle करेल)
             .anyExchange().permitAll()
         );

        return http.build();
    }
}