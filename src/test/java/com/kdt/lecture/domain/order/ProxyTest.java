package com.kdt.lecture.domain.order;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.BeforeEach;
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
public class ProxyTest {
    @Autowired
    EntityManagerFactory emf;

    private String uuid = UUID.randomUUID().toString();

    @BeforeEach
    void setUp(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Order order = new Order();
        order.setUuid(uuid);
        order.setMemo("호이잇!!");
        order.setOrderStatus(OrderStatus.OPENED);
        order.setOrderDateTime(LocalDateTime.now());

        em.persist(order);

        Member member = new Member();
        member.setName("bosub");
        member.setNickName("백둥이");
        member.setAddress("서울시 노원구");
        member.setAge(25);
        member.setDescription("개발자가 되고 파요...");
        member.addOrder(order);
        em.persist(member);

        transaction.commit();
    }

    @Test
    void proxyTest(){
        EntityManager em = emf.createEntityManager();
        Order order = em.find(Order.class, uuid);

        Member member = order.getMember();
        log.info("member use before is loaded : {}", emf.getPersistenceUnitUtil().isLoaded(member)); // 아직까지는 proxy 객체
        String nickName = member.getNickName();
        log.info("member use after is loaded : {}", emf.getPersistenceUnitUtil().isLoaded(member)); // 여기서 entity 화가 됨

    }

    @Test
    void movePersist(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        Order order = em.find(Order.class, uuid); // 영속 상태

        transaction.begin();

        // cascade 를 안하면 order item은 영속성 전이가 되지 않아서 order item 은 영속화 안 됨
        OrderItem orderItem = new OrderItem(); // 준영속 상태
        orderItem.setQuantity(15);
        orderItem.setPrice(5000);

        order.addOrderItem(orderItem); // casecade == all -> orderitem 이 영속화됨 영속성이 전이이
        transaction.commit(); //// flush
        // -----------------------------------

        em.clear();

        Order order2 = em.find(Order.class, uuid); // 영속 상태
        transaction.begin();

        order2.getOrderItems().remove(0); // 고아 상태 delete 쿼리는 실제로 날아가지 않음!
        // 고아객체 제거를 true로 하면, flush 하는 순간  rds에서도 삭제하겠다

        transaction.commit(); // flush
    }
}
