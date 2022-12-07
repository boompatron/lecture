package com.kdt.lecture.domain.order;

import com.kdt.lecture.domain.parent.Parent;
import com.kdt.lecture.domain.parent.ParentId;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

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

    @Test
    void mappedSuperClassTest(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderStatus(OrderStatus.OPENED);
        order.setMemo("----");
        order.setOrderDateTime(LocalDateTime.now());

        em.persist(order);

        transaction.commit();
    }

    @Test
    void multipleIdTest(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Parent parent = new Parent();
        parent.setId(new ParentId("id1", "id2"));

        em.persist(parent);

        transaction.commit();

        em.clear();

        Parent parent2 = em.find(Parent.class, new ParentId("id1", "id2"));
        log.info("{}, {}", parent2.getId().getId1(), parent2.getId().getId2());
    }
}
