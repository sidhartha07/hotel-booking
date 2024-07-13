package com.sidh.hotelbooking.rowmapper.customer;

import com.sidh.hotelbooking.model.customer.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Customer.builder()
                .customerId(rs.getString("a_cust_id"))
                .firstName(rs.getString("a_fst_nm"))
                .lastName(rs.getString("a_lst_nm"))
                .email(rs.getString("a_em"))
                .password(rs.getString("a_pwd"))
                .phone(rs.getString("a_phn"))
                .role(rs.getString("a_role"))
                .build();
    }
}
