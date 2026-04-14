package com.orderservice.dto;

import lombok.Data;

@Data
public class PaymentRequest {

	private Long orderId;
    private double amount;
}
