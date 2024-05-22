package com.sidh.hotelbooking.model.customer;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Customer implements Serializable {
    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String role;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
}
