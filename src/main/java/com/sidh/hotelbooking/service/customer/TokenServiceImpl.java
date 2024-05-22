package com.sidh.hotelbooking.service.customer;

import com.sidh.hotelbooking.config.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Override
    public String generateToken(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return jwtService.generateToken(secretKey, userDetails, jwtExpiration);
    }

    @Override
    public String generateRefreshToken(String authHeader) {
        final String jwt = authHeader.substring(7);
        return jwtService.generateRefreshToken(jwt, secretKey, jwtExpiration);
    }
}
