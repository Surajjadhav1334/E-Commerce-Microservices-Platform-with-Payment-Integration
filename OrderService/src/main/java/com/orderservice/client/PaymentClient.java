package com.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.orderservice.dto.PaymentRequest;

@FeignClient(name = "Payment-Service")
public interface PaymentClient {

	@PostMapping("/payment/create")
    String createPayment(@RequestBody PaymentRequest request);
	
	@GetMapping("/payment/{id}")
    PaymentRequest getPaymentById(@PathVariable("id") Long id);

    // 3. Update Payment (Status update karnyasaathi)
    @PutMapping("/payment/update/{id}")
    String updatePayment(@PathVariable("id") Long id, @RequestBody PaymentRequest request);

    // 4. Delete Payment (Jar order cancel zali tar)
    @DeleteMapping("/payment/delete/{id}")
    String deletePayment(@PathVariable("id") Long id);
}
