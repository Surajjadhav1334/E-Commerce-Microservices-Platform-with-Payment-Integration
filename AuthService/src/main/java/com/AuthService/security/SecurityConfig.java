package com.AuthService.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.AuthService.service.CustomUserService;

@Configuration
public class SecurityConfig {
	
	 @Autowired
	 private CustomUserService userDetailsService;

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder encoder) throws Exception {
	        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
	        builder.userDetailsService(userDetailsService)
	               .passwordEncoder(encoder);
	        return builder.build(); // Remove the .and()
	    }

	    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http.csrf(csrf -> csrf.disable())
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/auth/**").permitAll()
	                .anyRequest().authenticated()
	            )
	            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

	        return http.build();
	    }
	

}
