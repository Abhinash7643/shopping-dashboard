package com.abhinash.shoppingdashboard.service;

import com.abhinash.shoppingdashboard.entities.RefreshToken;
import com.abhinash.shoppingdashboard.exception.MyException;
import com.abhinash.shoppingdashboard.repository.RefreshTokenRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepos refreshTokenRepos;

    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepos.save(refreshToken);
    }

    void validateRefreshToken(String token) {
        refreshTokenRepos.findByToken(token)
                .orElseThrow(() -> new MyException("Invalid refresh Token"));
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepos.deleteByToken(token);
    }
}
