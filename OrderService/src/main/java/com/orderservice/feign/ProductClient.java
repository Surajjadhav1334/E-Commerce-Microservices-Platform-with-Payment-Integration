package com.orderservice.feign;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.orderservice.dto.Product;
import com.orderservice.entity.Order;

@FeignClient (name = "product-service", url = "http://localhost:8082")
public interface ProductClient {

	@GetMapping("/products/{id}")
    Product getProductById(@PathVariable Long id);
	
	@GetMapping("/products/{id}")
	Optional<Order> findById(Long productId);
}
