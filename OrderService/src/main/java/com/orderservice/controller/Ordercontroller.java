package com.orderservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderservice.dto.OrderRequest;
import com.orderservice.entity.Order;
import com.orderservice.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@PreAuthorize("hasAuthority('ROLE_USER')")
@RequestMapping("/orders")
public class Ordercontroller {
	
	@Autowired
	private OrderService orderService;
	
	   @PostMapping("/create")
	    public Order createOrder(@RequestBody Order order, HttpServletRequest request) {

	        String username = (String) request.getAttribute("username");

	        return orderService.createOrder(order, username);
	    }
	   
	   @GetMapping("/all")
	    public List<Order> getAllOrders() {
	        return orderService.getAllOrders();
	    }

	    @GetMapping("/{id}")
	    public Order getOrderById(@PathVariable Long id) {
	        return orderService.getOrderById(id);
	    }

	    @PutMapping("/update/{id}")
	    public Order updateOrder(@PathVariable Long id, @RequestBody OrderRequest dto) {
	    	
	        return orderService.updateOrder(id, dto);
	    }

	    @DeleteMapping("/delete/{id}")
	    public String deleteOrder(@PathVariable Long id) {
	        return orderService.deleteOrder(id);
	    }
    
}
