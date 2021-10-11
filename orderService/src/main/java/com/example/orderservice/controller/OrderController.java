package com.example.orderservice.controller;

import com.example.orderservice.domain.Order;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import javax.jms.Queue;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    public ModelMapper mapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrders(@PathVariable long id) throws JSONException {

        JSONObject responseBody = new JSONObject();

        Optional<Order> optionalOrder = orderService.findById(id);

        if (optionalOrder.isPresent()) {
            return new ResponseEntity<>(optionalOrder.get(), HttpStatus.OK);
        } else {
            responseBody.put("success", false);
            responseBody.put("message", "Order not found");
            return new ResponseEntity<>(responseBody.toString(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("all")
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @PostMapping()
    public Order save(@RequestBody OrderDto dto) {

        Order order = orderService.create(dto);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String orderAsJson = mapper.writeValueAsString(order);

            jmsTemplate.convertAndSend(queue, orderAsJson);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return order;

    }

}
