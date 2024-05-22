package com.sidh.hotelbooking.service.customer;

public interface TokenService {
    String generateToken(String email);
    String generateRefreshToken(String authHeader);
}
