package com.example.authenticationservice.service;

import com.example.authenticationservice.domain.Product;
import com.example.authenticationservice.dto.ProductDto;
import com.example.authenticationservice.repositiory.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product create(ProductDto request) {

        Product product = new Product(request.getName(), request.getVendor(), request.getCategory());

        return productRepository.save(product);

    }

}
