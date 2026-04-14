package com.productservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.productservice.dao.ProductRepository;
import com.productservice.entity.Product;


@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product addProduct(Product product) {
        return repository.save(product);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }
    
    public Product updateProduct(Long id, Product product) {

        Product existingProduct = repository.findById(id).orElse(null);

        if (existingProduct != null) {

            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setQuantity(product.getQuantity());

            return repository.save(existingProduct);
        }

        return null;
    }

    public Product getProductById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }
}
