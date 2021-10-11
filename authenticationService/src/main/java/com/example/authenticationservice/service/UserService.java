package com.example.authenticationservice.service;

import com.example.authenticationservice.domain.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {
    public void addUser(User user);
    public Optional<User> findUserByUsername(String firstName);

    public UserDetails getUserDetails(String firstName);
}
