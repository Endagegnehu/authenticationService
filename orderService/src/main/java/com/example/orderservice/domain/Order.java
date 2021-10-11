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
    private String totalCost;
    private String preferredPaymentType;

    public Order(String productList, String totalCost, String preferredPaymentType) {
        this.productList = productList;
        this.totalCost = totalCost;
         this.preferredPaymentType = preferredPaymentType;
    }

}
