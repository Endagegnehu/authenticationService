package com.example.authenticationservice.service;

import com.example.authenticationservice.domain.Product;
import com.example.authenticationservice.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product create(ProductDto flight);
    public List<Product> findAll();
    public Optional<Product> findById(long id);
}
