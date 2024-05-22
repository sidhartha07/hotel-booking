package com.sidh.hotelbooking.service.customer;

import com.sidh.hotelbooking.dto.customer.*;
import com.sidh.hotelbooking.model.customer.Customer;
import com.sidh.hotelbooking.repository.customer.CustomerRepository;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.sidh.hotelbooking.util.CustomerCmnConstants.USER;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Value("${token.disabled:#{null}}")
    private Boolean tokenDisabled;

    @Override
    public MessageDto register(CustomerRegisterDto customerRegisterDto) {
        if (customerRegisterDto != null && !ObjectUtils.isEmpty(customerRegisterDto)) {
            String encodedPassword = passwordEncoder.encode(customerRegisterDto.getPassword());
            Customer customer = Customer.builder()
                    .firstName(customerRegisterDto.getFirstName())
                    .lastName(customerRegisterDto.getLastName())
                    .email(customerRegisterDto.getEmail())
                    .password(encodedPassword)
                    .phone(customerRegisterDto.getPhone())
                    .role(USER)
                    .build();
            try {
                customerRepository.create(customer);
            } catch (RuntimeException ex) {
                throw ex;
            }
        }
        return MessageDto.builder()
                .message("Customer Registered successfully")
                .build();
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        Customer customer = null;
        if (StringUtils.hasText(request.getEmail())) {
            customer = customerRepository.findUserByEmail(request.getEmail());
        }
        if (customer == null || ObjectUtils.isEmpty(customer) ||
                !passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            throw new RuntimeException("Login failed");
        }
        CustomerDto customerDto = CustomerDto.builder()
                .customerId(customer.getCustomerId())
                .email(customer.getEmail())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .role(customer.getRole())
                .phone(customer.getPhone())
                .build();
        String token = null;
        if (tokenDisabled != null && BooleanUtils.isFalse(tokenDisabled)) {
            token = tokenService.generateToken(customer.getEmail());
        }
        return LoginResponseDto.builder()
                .message("Customer Login successful")
                .token(token)
                .customers(List.of(customerDto))
                .build();
    }
}
