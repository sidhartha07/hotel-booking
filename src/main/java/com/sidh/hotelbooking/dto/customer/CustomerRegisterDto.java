package com.sidh.hotelbooking.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomerRegisterDto implements Serializable {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
}
