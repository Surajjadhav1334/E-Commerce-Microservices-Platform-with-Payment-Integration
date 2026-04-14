package com.productservice.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.productservice.entity.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {

}