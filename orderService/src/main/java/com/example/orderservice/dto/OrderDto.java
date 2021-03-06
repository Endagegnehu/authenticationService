package com.example.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String productList;
    private String ccv;
    private String preferredPayment;
    private String totalCost;
    private String preferredPaymentType;
}
