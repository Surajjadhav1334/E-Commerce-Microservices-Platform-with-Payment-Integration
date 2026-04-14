package com.paymentservice.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.razorpay.RazorpayClient;

@Configuration
public class RazorpayConfig {

	 @Value("${razorpay.api.key}")
	    private String key;

	    @Value("${razorpay.api.secret}")
	    private String secret;

	    @Bean
	    public RazorpayClient razorpayClient() {
	        try {
	            return new RazorpayClient(key, secret);
	        } catch (Exception e) {
	            throw new RuntimeException("Razorpay client creation failed", e);
	        }
	    }
}
