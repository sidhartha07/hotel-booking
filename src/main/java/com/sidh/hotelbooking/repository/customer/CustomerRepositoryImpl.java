package com.sidh.hotelbooking.repository.customer;

import com.sidh.hotelbooking.mapper.customer.CustomerRowMapper;
import com.sidh.hotelbooking.model.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sidh.hotelbooking.util.CustomerCmnConstants.*;
import static com.sidh.hotelbooking.util.CustomerQueries.FIND_BY_EMAIL;
import static com.sidh.hotelbooking.util.CustomerQueries.INSERT_CUSTOMER;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final Logger logger = LoggerFactory.getLogger(CustomerRepositoryImpl.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void create(Customer customer) {
        int inscnt = 0;
        if (!ObjectUtils.isEmpty(customer)) {
            inscnt = jdbcTemplate.update(INSERT_CUSTOMER, getSqlParamSource(customer));
        }
        logger.info("insert to t_cust completed with {} count", inscnt);
    }

    @Override
    public Customer findUserByEmail(String email) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue(EMAIL, email);
        List<Customer> customers = jdbcTemplate.query(FIND_BY_EMAIL, map, new CustomerRowMapper());
        logger.info("customer fetch completed with {} count", customers.size());
        return !CollectionUtils.isEmpty(customers) ? customers.stream().findFirst().get() : null;
    }

    private MapSqlParameterSource getSqlParamSource(Customer customer) {
        Map<String, Object> map = new HashMap<>();
        map.put(FIRST_NAME, customer.getFirstName());
        map.put(LAST_NAME, customer.getLastName());
        map.put(EMAIL, customer.getEmail());
        map.put(PASSWORD, customer.getPassword());
        map.put(PHONE, customer.getPhone());
        map.put(ROLE, customer.getRole());
        map.put(CREATED_DATE_TIME, LocalDateTime.now());
        map.put(UPDATED_DATE_TIME, LocalDateTime.now());
        return new MapSqlParameterSource(map);
    }
}
