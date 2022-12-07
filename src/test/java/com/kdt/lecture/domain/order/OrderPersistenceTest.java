package com.kdt.lecture.domain.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.kdt.lecture.domain.order.OrderStatus.OPENED;

@Slf4j
@SpringBootTest
public class OrderPersistenceTest {
    @Autowired
    EntityManagerFactory emf;

    @Test
    @DisplayName("멤버 추가 테스트")
    void memberInsertTest () {
        Member member = new Member();
        member.setAge(25);
        member.setName("백둥이");
        member.setNickName("Bosub Kim");
        member.setAddress("서울시 노원구");
        member.setDescription("백엔드 개발자 하고파요");

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(member);

        transaction.commit();
    }

    @Test
    void 잘못된_설계() {
        Member member = new Member();
        member.setName("kanghonggu");
        member.setAddress("서울시 동작구(만) 움직이면 쏜다.");
        member.setAge(33);
        member.setNickName("guppy.kang");
        member.setDescription("백앤드 개발자에요.");

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(member);
        Member memberEntity = entityManager.find(Member.class, 1L);

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderDateTime(LocalDateTime.now());
        order.setOrderStatus(OPENED);
        order.setMemo("부재시 전화주세요.");
        order.setMemberId(memberEntity.getId()); // 외래키를 직접 지정

        entityManager.persist(order);
        transaction.commit();

        Order orderEntity = entityManager.find(Order.class, order.getUuid());
        // FK 를 이용해 회원 다시 조회
        Member orderMemberEntity = entityManager.find(Member.class, orderEntity.getMemberId());
        // orderEntity.getMember() // 객체중심 설계라면 객체그래프 탐색을 해야하지 않을까?
        log.info("nick : {}", orderMemberEntity.getNickName());
    }

    @Test
    @DisplayName("연관관계 테스트")
    void relationshipTest(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Member member = new Member();
        member.setName("bosub");
        member.setNickName("백둥이");
        member.setAddress("서울시 노원구");
        member.setAge(25);

        em.persist(member);

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderStatus(OPENED);
        order.setOrderDateTime(LocalDateTime.now());
        order.setMemo("판교로 가고파요");
        order.setMember(member);

        em.persist(order);

        transaction.commit();

        em.clear(); // 쿼리를 보기 위해서 clear 해줌
        Order entity = em.find(Order.class, order.getUuid());

        log.info("{}", entity.getMember().getNickName()); // 객체 그래프 탐색
        log.info("{}", entity.getMember().getOrders().size());
        log.info("{}", order.getMember().getOrders().size());
    }
}
