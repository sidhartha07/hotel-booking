package com.sidh.hotelbooking.repository.customer;

import com.sidh.hotelbooking.model.customer.Customer;

public interface CustomerRepository {
    void create(Customer customer);
    Customer findUserByEmail(String email);
}
