package com.abhinash.shoppingdashboard.repository;

import com.abhinash.shoppingdashboard.entities.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RefreshTokenRepos extends MongoRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
