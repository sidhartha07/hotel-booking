package com.sidh.hotelbooking.service.customer;

import com.sidh.hotelbooking.dto.customer.*;
import com.sidh.hotelbooking.exception.InvalidRequestException;
import com.sidh.hotelbooking.model.customer.Customer;
import com.sidh.hotelbooking.repository.customer.CustomerRepository;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
    public MessageDto register(CustomerRegisterDto register) {
        if (register.getPhone().length() != 10 || !register.getPhone().matches("^\\d{10}$")) {
            throw new InvalidRequestException(HttpStatus.BAD_REQUEST, mapToMessageDto(HttpStatus.BAD_REQUEST.toString(),
                    "Invalid phone number."));
        }
        if (register != null && !ObjectUtils.isEmpty(register)) {
            String encodedPassword = passwordEncoder.encode(register.getPassword());
            Customer customer = Customer.builder()
                    .firstName(register.getFirstName())
                    .lastName(register.getLastName())
                    .email(register.getEmail())
                    .password(encodedPassword)
                    .phone(register.getPhone())
                    .role(USER)
                    .build();
            try {
                customerRepository.create(customer);
            } catch (RuntimeException ex) {
                throw new InvalidRequestException(HttpStatus.INTERNAL_SERVER_ERROR, mapToMessageDto(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                        "Email or Phone already exists. Please try with another."));
            }
        } else {
            throw new InvalidRequestException(HttpStatus.BAD_REQUEST, mapToMessageDto(HttpStatus.BAD_REQUEST.toString(),
                    "Invalid request. Please provide mandatory fields."));
        }
        return MessageDto.builder()
                .message("Customer Registered successfully")
                .build();
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        Customer customer = null;
        if (StringUtils.hasText(request.getEmail()) && StringUtils.hasText(request.getPassword())) {
            customer = customerRepository.findUserByEmail(request.getEmail());
        }
        if (customer == null || ObjectUtils.isEmpty(customer) ||
                !passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            throw new InvalidRequestException(HttpStatus.BAD_REQUEST, mapToMessageDto(HttpStatus.BAD_REQUEST.toString(),
                    "Invalid email or password."));
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

    private static MessageDto mapToMessageDto(String status, String message) {
        return MessageDto.builder()
                .status(status)
                .message(message)
                .build();
    }
}
