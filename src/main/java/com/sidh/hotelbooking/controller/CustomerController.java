package com.sidh.hotelbooking.controller;

import com.sidh.hotelbooking.dto.customer.CustomerRegisterDto;
import com.sidh.hotelbooking.dto.customer.LoginRequestDto;
import com.sidh.hotelbooking.dto.customer.LoginResponseDto;
import com.sidh.hotelbooking.dto.customer.MessageDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CustomerService customerService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> register(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
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
//        if (!StringUtils.hasText(request.getEmail()) ||
//                !StringUtils.hasText(request.getPassword()) ||
//                !StringUtils.hasText(request.getFullName()) ||
//                !StringUtils.hasText(request.getRole()) ||
//                !StringUtils.hasText(request.getPhoneNo())) {
//            MessageDto messageDto = MessageDto.builder()
//                    .status("400")
//                    .message("Please provide mandatory fields")
//                    .build();
//            throw new InvalidRequestException(HttpStatus.BAD_REQUEST, messageDto);
//        }
        MessageDto response = customerService.register(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> login(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            description = "Provide email and password to login",
            content = {@Content(schema = @Schema(name = "LoginRequestDto", example = """
                    {
                      "email": "string",
                      "password": "string"
                    }
                    """))}) @RequestBody LoginRequestDto request) {
//        if (!StringUtils.hasText(request.getEmail()) ||
//                !StringUtils.hasText(request.getPassword())) {
//            MessageDto messageDto = MessageDto.builder()
//                    .status("400")
//                    .message("Please provide mandatory fields")
//                    .build();
//            throw new InvalidRequestException(HttpStatus.BAD_REQUEST, messageDto);
//        }
        LoginResponseDto response = customerService.login(request);
//        ResponseDto response = ResponseDto.builder()
//                .status("200")
//                .message("User logged in successfully")
//                .data(List.of(data))
//                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
