package com.productservice.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

@Component
public class JwtFilter extends OncePerRequestFilter {

 @Autowired
 private JwtUtil jwtUtil;

 @Override
 protected void doFilterInternal(HttpServletRequest request,
   HttpServletResponse response,
   FilterChain filterChain) throws ServletException, IOException {

  String authHeader = request.getHeader("Authorization");

  if (authHeader != null && authHeader.startsWith("Bearer ")) {

   String token = authHeader.substring(7);

   try {

	    if (!jwtUtil.validateToken(token)) {
	        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
	        return;
	    }

	} catch (Exception e) {
	    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Error");
	    return;
	}

  }

  filterChain.doFilter(request, response);
 }

}