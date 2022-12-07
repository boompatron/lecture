package com.kdt.lecture.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Slf4j
@SpringBootTest
public class PersistenceContextTest {
    @Autowired
    CustomerRepository repository;

    @Autowired
    EntityManagerFactory emf;

    @BeforeEach
    void setUp(){
        repository.deleteAll();
    }

    @Test
    @DisplayName("저장 테스트")
    void saveTest () {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer(); // 아직까지는 비영속 상태
        customer.setId(1L);
        customer.setFirstName("Bosub");
        customer.setLastName("Kim");

        em.persist(customer); // 영속화!

        transaction.commit(); // em.flush(); 쓰지 지연 저장소의 쿼리가 이제서야 실행이 됨!

    }
    
    @Test
    @DisplayName("조회 테스트")
    void readTest () {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer(); // 아직까지는 비영속 상태
        customer.setId(1L);
        customer.setFirstName("Bosub");
        customer.setLastName("Kim");

        em.persist(customer); // 영속화!

        transaction.commit(); // em.flush();

        em.detach(customer); // 준영속화!

        Customer selected = em.find(Customer.class, 1L);
        log.info("{}, {}", selected.getFirstName(), selected.getLastName());
    }

    @Test
    @DisplayName("1차 캐시 이용 조회 테스트")
    void readUsingFirstCacheTest () {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer(); // 아직까지는 비영속 상태
        customer.setId(1L);
        customer.setFirstName("Bosub");
        customer.setLastName("Kim");

        em.persist(customer); // 영속화!

        transaction.commit(); // em.flush();

        Customer selected = em.find(Customer.class, 1L);
        log.info("{}, {}", selected.getFirstName(), selected.getLastName());
    }

    @Test
    @DisplayName("수정 테스트")
    void updateTest () {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer(); // 아직까지는 비영속 상태
        customer.setId(1L);
        customer.setFirstName("Bosub");
        customer.setLastName("Kim");

        em.persist(customer); // 영속화!

        transaction.commit(); // em.flush();

        transaction.begin();

        customer.setFirstName("updated!!");
        customer.setLastName("name!");

        transaction.commit();
    }

    @Test
    @DisplayName("삭제 테스트")
    void removeTest () {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer(); // 아직까지는 비영속 상태
        customer.setId(1L);
        customer.setFirstName("Bosub");
        customer.setLastName("Kim");

        em.persist(customer); // 영속화!

        transaction.commit(); // em.flush();


        transaction.begin();

        em.remove(customer);

        transaction.commit();
    }
}
