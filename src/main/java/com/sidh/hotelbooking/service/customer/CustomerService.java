package com.sidh.hotelbooking.service.customer;

import com.sidh.hotelbooking.dto.customer.CustomerRegisterDto;
import com.sidh.hotelbooking.dto.customer.LoginRequestDto;
import com.sidh.hotelbooking.dto.customer.LoginResponseDto;
import com.sidh.hotelbooking.dto.customer.MessageDto;

public interface CustomerService {
    MessageDto register(CustomerRegisterDto customerRegisterDto);
    LoginResponseDto login(LoginRequestDto loginRequestDto);
}
