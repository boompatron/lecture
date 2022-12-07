package com.kdt.lecture.domain.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Slf4j
@SpringBootTest
public class ImproveMappingTest {
    @Autowired
    private EntityManagerFactory emf;

    @Test
    void inheritanceTest(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Food food = new Food();
        food.setPrice(20000);
        food.setStockQuantity(240);
        food.setChef("백종원");

        em.persist(food);

        transaction.commit();
    }
}
