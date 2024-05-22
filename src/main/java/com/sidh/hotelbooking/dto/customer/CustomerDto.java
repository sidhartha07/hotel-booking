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
public class CustomerDto implements Serializable {
    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String role;
}
