package com.orderservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderservice.client.PaymentClient;
import com.orderservice.dao.Orderrepository;
import com.orderservice.dto.OrderRequest;
import com.orderservice.dto.PaymentRequest;
import com.orderservice.dto.Product;
import com.orderservice.entity.Order;
import com.orderservice.feign.ProductClient;

@Service
public class OrderService {

	@Autowired
	private Orderrepository orderrepository;

	@Autowired
	private ProductClient productClient;

	@Autowired
	private PaymentClient paymentClient;

	public Order createOrder(Order order, String username) {

		order.setUsername(username);

		Product product = productClient.getProductById(order.getProductId());

		if (product == null) {
			throw new RuntimeException("Product not found");
		}

		order.setProductName(product.getName());
		order.setPrice(product.getPrice());

		// 👉 Save Order first
		Order savedOrder = orderrepository.save(order);

		// 👉 CALL PAYMENT SERVICE 🔥
		PaymentRequest request = new PaymentRequest();
		request.setOrderId(savedOrder.getId());
		request.setAmount(savedOrder.getPrice() * savedOrder.getQuantity());

		String paymentResponse = paymentClient.createPayment(request);

		System.out.println("Payment Response: " + paymentResponse);

		return savedOrder;
	}

	public List<Order> getAllOrders() {
		return orderrepository.findAll();
	}

	// READ BY ID
	public Order getOrderById(Long id) {
		return orderrepository.findById(id).orElse(null);
	}

	// UPDATE
	public Order updateOrder(Long id, OrderRequest dto) {

//		Order order = orderrepository.findById(id).orElse(null);
		
		  Order order = orderrepository.findById(id)
			        .orElseThrow(() -> new RuntimeException("Order not found"));

			    Product product = productClient.getProductById(dto.getProductId());
			    if (product == null) {
			        throw new RuntimeException("Product not found");
			    }

			    // 3. Update fields
			    order.setProductId(product.getId());          // 👈 important
			    order.setProductName(product.getName());      // 👈 fix
			    order.setPrice(product.getPrice());           // 👈 fix
			    order.setQuantity(dto.getQuantity());

			    // 4. Save
			    return orderrepository.save(order);

	}

	// DELETE
	public String deleteOrder(Long id) {

		orderrepository.deleteById(id);

		return "Order deleted successfully";
	}
}
