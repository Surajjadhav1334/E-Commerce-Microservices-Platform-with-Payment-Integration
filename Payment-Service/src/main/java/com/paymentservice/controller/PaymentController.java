package com.paymentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymentservice.dto.PaymentRequest;
import com.paymentservice.entity.Payment;
import com.paymentservice.service.PaymentService;
import com.razorpay.RazorpayException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	 private PaymentService paymentService;

	    // ✅ Create Order API
	    @PostMapping("/create")
	    public Payment createOrder(@RequestBody PaymentRequest request) throws RazorpayException {

	        System.out.println("Payment Order Created");

	        return paymentService.createPaymentOrder(
	                request.getOrderId(),
	                (int) request.getAmount()
	        );
	    }

	    // ✅ Verify API (manual testing)
	    @PostMapping("/verify")
	    public Payment verify(
	            @RequestParam String orderId,
	            @RequestParam String razorpayPaymentId
	    ) {
	     	System.out.println("Order Verified");
	        return paymentService.verifyPayment(orderId, razorpayPaymentId);
	    }
	    
	    @PostMapping("/cancel")
	    public Payment cancelPayment(@RequestParam String orderId) {

	        System.out.println("Payment Cancelled");

	        return paymentService.cancelPayment(orderId);
	    }
}
