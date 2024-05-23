package com.sidh.hotelbooking.controller;

import com.sidh.hotelbooking.dto.customer.CustomerRegisterDto;
import com.sidh.hotelbooking.dto.customer.LoginRequestDto;
import com.sidh.hotelbooking.dto.customer.LoginResponseDto;
import com.sidh.hotelbooking.dto.customer.MessageDto;
import com.sidh.hotelbooking.exception.InvalidRequestException;
import com.sidh.hotelbooking.service.customer.CustomerService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CustomerService customerService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageDto> register(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            description = "Provide user details for registration",
            content = {@Content(schema = @Schema(name = "RegistrationRequestDto", example = """
                    {
                      "email": "string",
                      "password": "string",
                      "firstName": "string",
                      "lastName": "string",
                      "phone": "string"
                    }
                    """))}) @RequestBody CustomerRegisterDto request) {
        if (!StringUtils.hasText(request.getEmail()) ||
                !StringUtils.hasText(request.getPassword()) ||
                !StringUtils.hasText(request.getFirstName()) ||
                !StringUtils.hasText(request.getLastName()) ||
                !StringUtils.hasText(request.getPhone())) {
            MessageDto messageDto = MessageDto.builder()
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .message("Please provide mandatory fields.")
                    .build();
            throw new InvalidRequestException(HttpStatus.BAD_REQUEST, messageDto);
        }
        MessageDto response = customerService.register(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponseDto> login(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            description = "Provide email and password to login",
            content = {@Content(schema = @Schema(name = "LoginRequestDto", example = """
                    {
                      "email": "string",
                      "password": "string"
                    }
                    """))}) @RequestBody LoginRequestDto request) {
        LoginResponseDto response = customerService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler(value = {InvalidRequestException.class})
    public ResponseEntity<Object> handleCustomerException(InvalidRequestException ex) {
        logger.warn("Customer operation failed with Error - {}", ex.getError());
        return ResponseEntity.status(ex.getStatus().value())
                .header("produces", MediaType.APPLICATION_JSON_VALUE)
                .body(ex.getMessageDto());
    }
}
