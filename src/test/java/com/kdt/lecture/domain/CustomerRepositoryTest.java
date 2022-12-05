package com.kdt.lecture.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository repository;

//    @Test
//    @DisplayName("")
//    void generate_db () {
//        try {
//            Class.forName("org.h2.Driver");
//            Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
//
//            Statement s = con.createStatement();
//            s.executeUpdate("drop table customers if exists");
//            s.executeUpdate("create table customers(id serial, first_name varchar(255), last_name(255))");
//
//            s.close();
//            con.close();
//        }catch (Exception e){
//
//        }
//    }


    @Test
    void test () {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Bosub");
        customer.setLastName("Kim");

        // When
        repository.save(customer);

        // Then
        Customer entity = repository.findById(1L).get();
        log.info("{} {}", entity.getFirstName(), entity.getLastName());

    }
}