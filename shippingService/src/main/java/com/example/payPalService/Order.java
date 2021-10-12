package com.example.payPalService;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    private String productList;
    private String ccv;
    private String preferredPayment;
    private String totalCost;
    @JsonIgnore
    private String status;

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public Order(String productList, String ccv, String preferredPayment, String totalCost) {
        this.productList = productList;
        this.ccv = ccv;
        this.preferredPayment = preferredPayment;
        this.totalCost = totalCost;
    }
}
