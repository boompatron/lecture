package com.kdt.lecture.domain.order;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class OrderRepositoryTest {
    @Autowired
    OrderRepository repository;

    @Test
    void test(){
        Order order = new Order();
        String uuid = UUID.randomUUID().toString();
        order.setUuid(uuid);
        order.setOrderStatus(OrderStatus.OPENED);
        order.setCreatedBy("BBBBBB");
        order.setOrderDateTime(LocalDateTime.now());
        order.setMemo("memo!!");
        order.setCreatedAt(LocalDateTime.now());

        repository.save(order);

        Order retrievedOrder =  repository.findById(uuid).get();
        List<Order> allOrders = repository.findAll();

        repository.findAllByOrderStatus(OrderStatus.OPENED);
        repository.findAllByOrderStatusOrderByOrderDateTime(OrderStatus.OPENED);

        repository.findByMemo("memo!!");
    }
}