package com.AuthService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AuthService.entity.User;
import com.AuthService.repository.UserRepository;
import com.AuthService.security.JwtUtillity;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired 
    private UserRepository userRepository;
    @Autowired 
    private PasswordEncoder passwordEncoder;
    @Autowired 
    private JwtUtillity jwtUtil;
    
    @PostMapping("/register-admin")
    public ResponseEntity<String> registerAdmin(@RequestBody User user){
    	
    	if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("Adminname already exists ❌");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_ADMIN");

        userRepository.save(user);

        return ResponseEntity.ok("Admin Registered Sucessfully");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
    	
    	 if (userRepository.findByUsername(user.getUsername()).isPresent()) {
    	        return ResponseEntity
    	                .badRequest()
    	                .body("Username already exists ❌");
    	    }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully ✅");
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {

        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        User dbUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return jwtUtil.generateToken(dbUser.getUsername(), dbUser.getRole());
    }
}
