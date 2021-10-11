package com.example.orderservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ID;

    private String productList;
    private String ccv;
    private String preferredPayment;
    private String totalCost;


    public Order(String productList, String ccv, String preferredPayment, String totalCost) {
        this.productList = productList;
        this.ccv = ccv;
        this.preferredPayment = preferredPayment;
        this.totalCost = totalCost;
    }
}
