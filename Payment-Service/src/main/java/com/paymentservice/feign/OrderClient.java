package com.paymentservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.paymentservice.dto.OrderDTO;

@FeignClient(name = "Order-Service", url = "http://localhost:8083")
public interface OrderClient {
	
	 @GetMapping("/orders/{id}")
	    OrderDTO getOrderById(@PathVariable Long id);

}
