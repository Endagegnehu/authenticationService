package com.example.authenticationservice.controller;

import com.example.authenticationservice.domain.User;
import com.example.authenticationservice.dto.UserRegistrationResponse;
import com.example.authenticationservice.service.UserService;
import com.example.authenticationservice.service.UserServiceImpl;
import com.example.authenticationservice.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController

public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserServiceImpl userServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        user.setRole("ROLE_USER");
        userService.addUser(user);
        final Optional<User> currentUser = userService.findUserByUsername(user.getUsername());
        final UserDetails userDetails = userServiceImpl.getUserDetails(user.getUsername());
        final String jwt  = jwtUtil.generateToken(userDetails);
        logger.info("JWT: " + jwt);
        return ResponseEntity.ok(new UserRegistrationResponse(jwt));
    }
    @GetMapping("/user")
    public String getUser(){
        return "<h1>User</h1>";
    }
}
