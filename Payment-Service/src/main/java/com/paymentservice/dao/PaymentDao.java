package com.paymentservice.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymentservice.entity.Payment;

@Repository
public interface PaymentDao extends JpaRepository<Payment, Long> {
	
	Optional<Payment> findByRazorpayOrderId(String orderId);

	Optional<Payment> findFirstByOrderServiceId(Long orderId);
}
