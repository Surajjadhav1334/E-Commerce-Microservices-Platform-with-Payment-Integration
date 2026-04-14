package com.paymentservice.dto;


import lombok.Data;

@Data
public class PaymentRequest {

	private Long orderId;
    private double amount;
}
