package com.kdt.lecture;

import com.kdt.lecture.repository.CustomerRepository;
import com.kdt.lecture.repository.domain.CustomerEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
public class JPATest {

    @Autowired
    CustomerRepository repository;

    @BeforeEach
    void setUo(){}

    @AfterEach
    void tearDown(){
        repository.deleteAll();
    }

    @Test
    void insertTest(){
        // Given
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);
        customer.setFirstName("Bosub");
        customer.setLastName("Kim");

        // When
        repository.save(customer);

        // Then
        CustomerEntity entity = repository.findById(1L).get();
        log.info("{} {}", entity.getFirstName(), entity.getLastName());
    }

    @Test
    @Transactional
    void updateTest(){
        // Given
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);
        customer.setFirstName("Bosub");
        customer.setLastName("Kim");
        customer.setAge(25);
        repository.save(customer);

        // When
        CustomerEntity entity = repository.findById(1L).get();
        entity.setFirstName("corgy");
        entity.setLastName("Coco");

        // Then
        CustomerEntity updated = repository.findById(1L).get();
        log.info("{} {} {}", updated.getFirstName(), updated.getLastName(), updated.getAge());
    }
}
