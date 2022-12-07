package com.kdt.lecture.domain.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id") // 명시하면 직접 생성 전략
    private String uuid;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDateTime;

    @Lob
    private String memo;

    @Column(name = "member_id", insertable = false, updatable = false) // fk
    private Long memberId;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    public void setMember(Member member){
        if(Objects.nonNull(this.member)){
            member.getOrders().remove(this);
        }

        this.member = member;
        member.getOrders().add(this);
        // 연관관계 편의 메소드
    }
}
