package com.kdt.lecture;

import com.kdt.lecture.legacy.repository.CustomerMapper;
import com.kdt.lecture.legacy.repository.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
@SpringBootTest
public class MyBatisTest {
    static final String DROP_TABLE_SQL = "drop table customers if exists";
    static final String CREATE_TABLE_SQL = "CREATE TABLE customers(id SERIAL, first_name varchar(255), last_name varchar(255))";

    @Autowired
    JdbcTemplate jdbcTemplate;;

    @Autowired
    CustomerMapper customerMapper;

    @Test
    void save_test(){
        jdbcTemplate.update(DROP_TABLE_SQL);
        jdbcTemplate.update(CREATE_TABLE_SQL);

        customerMapper.save(new Customer(1L, "bosub", "kim"));
        Customer customer = customerMapper.findById(1L);

        log.info("full Name : {}, {}", customer.getFirstName(), customer.getLastName());
    }
}
