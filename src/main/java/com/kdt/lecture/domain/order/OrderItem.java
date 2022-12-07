package com.kdt.lecture.domain.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "order_item")
public class OrderItem extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int price;

    private int quantity;

    // fk
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "item_id")
    private Long itemId;

    @OneToMany(mappedBy = "orderItem")
    private List<Item> items = new ArrayList<>();
}
