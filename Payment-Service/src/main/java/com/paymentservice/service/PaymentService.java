package com.paymentservice.service;

import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.paymentservice.dao.PaymentDao;
import com.paymentservice.dto.OrderDTO;
import com.paymentservice.entity.Payment;
import com.paymentservice.feign.OrderClient;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
//import com.razorpay.*;
//import com.paymentservice.dto.OrderDTO;
//import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class PaymentService {

	@Autowired
	PaymentDao paymentDao;

	@Autowired
	RazorpayClient client;
	
	@Autowired
	OrderClient orderClient;

	public PaymentService(@Value("${razorpay.api.key}") String key, @Value("${razorpay.api.secret}") String secret,
			PaymentDao paymentDao) throws RazorpayException {
		this.client = new RazorpayClient(key, secret);
		this.paymentDao = paymentDao;
	}

	 public Payment createPaymentOrder(Long orderId, int amount) throws RazorpayException {
		 
		 OrderDTO order = orderClient.getOrderById(orderId);
		 
		 Optional<Payment> existing = paymentDao.findFirstByOrderServiceId(orderId);
		    if(existing.isPresent()) {
		        return existing.get();
		    }

		    if(order == null) {
		        throw new RuntimeException("Order not found");
		    }
		

	        JSONObject options = new JSONObject();
	        options.put("amount", amount * 100);
	        options.put("currency", "INR");
	        options.put("receipt", "txn_" + System.currentTimeMillis());

	        Order razorpayOrder = client.orders.create(options);

	        Payment payment = new Payment();
	        payment.setOrderServiceId(orderId);
	        payment.setRazorpayOrderId(razorpayOrder.get("id"));
	        payment.setAmount(amount);
	        payment.setStatus("PENDING");

	        return paymentDao.save(payment);
	    }

	// ✅ Verify Payment
	  public Payment verifyPayment(String orderId, String razorpayPaymentId) {

	        Payment payment = paymentDao.findFirstByOrderServiceId(Long.parseLong(orderId))
	                .orElseThrow(() -> new RuntimeException("Order not found"));

	        payment.setRazorpayPaymentId(razorpayPaymentId);
	        payment.setStatus("SUCCESS");

	        return paymentDao.save(payment);
	    }
	  
	  public Payment cancelPayment(String orderId) {

		    Payment payment = paymentDao.findByRazorpayOrderId(orderId)
		            .orElseThrow(() -> new RuntimeException("Order not found"));

		    payment.setStatus("FAILED"); // किंवा CANCELLED

		    return paymentDao.save(payment);
		}

}
