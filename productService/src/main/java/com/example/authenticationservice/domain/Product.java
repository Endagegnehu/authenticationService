package com.example.authenticationservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ID;
    private String name;
    private String vendor;
    private String category;

    public Product(String name, String vendor, String category) {
        this.name = name;
        this.vendor = vendor;
        this.category = category;
    }
}
