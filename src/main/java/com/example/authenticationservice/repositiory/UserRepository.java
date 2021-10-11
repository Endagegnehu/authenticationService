package com.example.authenticationservice.repositiory;

import com.example.authenticationservice.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findByFirstName(String firsName);
}
