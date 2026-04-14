package com.orderservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orderservice.entity.Order;

@Repository
public interface Orderrepository extends JpaRepository<Order, Long> {

}
