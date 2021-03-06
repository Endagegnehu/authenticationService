package com.example.authenticationservice.dto;

import com.example.authenticationservice.util.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRegistrationRequest {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String shippingAddress;
    private PaymentMethod paymentMethod;
}
