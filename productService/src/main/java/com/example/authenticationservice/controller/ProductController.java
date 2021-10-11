package com.example.authenticationservice.controller;

import com.example.authenticationservice.domain.Product;
import com.example.authenticationservice.dto.ProductDto;
import com.example.authenticationservice.service.ProductService;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/products")
public class ProductController {


    @Autowired
    private ProductService productService;

    @Autowired
    public ModelMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProducts(@PathVariable long id) throws JSONException {

        JSONObject responseBody = new JSONObject();

        Optional<Product> optionalProduct = productService.findById(id);

        if (optionalProduct.isPresent()) {
            return new ResponseEntity<>(optionalProduct.get(), HttpStatus.OK);
        } else {
            responseBody.put("success", false);
            responseBody.put("message", "Passenger not found");
            return new ResponseEntity<>(responseBody.toString(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("all")
    public List<Product> findAll() {
        return productService.findAll();
    }

    @PostMapping
    public Product save(@RequestBody ProductDto dto) {
        return productService.create(dto);
    }

}
