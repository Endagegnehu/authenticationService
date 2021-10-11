package com.example.authenticationservice.service;

import com.example.authenticationservice.domain.User;
import com.example.authenticationservice.repositiory.UserRepository;
import com.example.authenticationservice.util.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findUserByFirstName(String firstName) {
       return userRepository.findByFirstName(firstName);
    }

    @Override
    public UserDetails getUserDetails(String firstName) {
        Optional<User> user = userRepository.findByFirstName(firstName);
        user.orElseThrow(()-> new UsernameNotFoundException("No user found: "+ firstName));
        return user.map(UserDetailsImpl::new).get();
    }

}
