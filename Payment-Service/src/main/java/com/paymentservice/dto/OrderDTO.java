package com.paymentservice.dto;


import lombok.Data;

@Data
public class OrderDTO {

    private Long id;
    private String productName;
    private int quantity;
    private double price;
    private String username;
}