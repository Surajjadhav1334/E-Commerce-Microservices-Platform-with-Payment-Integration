package com.orderservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OrderRequest {
    private Long productId;
    private int quantity;
    private double price;
}