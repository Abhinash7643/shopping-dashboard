package com.abhinash.shoppingdashboard.repository;

import com.abhinash.shoppingdashboard.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepos extends MongoRepository<User, String> {

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<Boolean> existsByUsername(String username);

    Optional<Boolean> existsByEmail(String email);
}
