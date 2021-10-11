package com.example.orderservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Payment {
    @Id
    @Column(name = "id", nullable = false)
    @JsonIgnore
    private Long id;
    private String productList;
    private String ccv;
    private String preferredPayment;
    private String totalCost;
    @JsonIgnore
    private String status;

    private void setId(Long id) {
        this.id = id;
    }



}
