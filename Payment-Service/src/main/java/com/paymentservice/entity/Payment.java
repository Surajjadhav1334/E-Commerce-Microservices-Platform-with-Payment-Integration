package com.paymentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private Long orderServiceId; // 👈 तुझ्या order service मधला ID
	private String razorpayOrderId; // 👈 Razorpay order id
	private String razorpayPaymentId; // Payment success nantar milato
	private int amount;
	private String status;

}
