package com.example.payPalService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;


@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@RequestMapping
public class PayPalServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayPalServiceApplication.class, args);
    }

}
