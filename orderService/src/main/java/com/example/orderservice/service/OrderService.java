package com.example.orderservice.service;


import com.example.orderservice.domain.Order;
import com.example.orderservice.dto.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Order create(OrderDto flight);
    public List<Order> findAll();
    public Optional<Order> findById(long id);
}
