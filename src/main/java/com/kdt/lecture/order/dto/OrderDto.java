package com.kdt.lecture.order.dto;

import com.kdt.lecture.domain.order.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String uuid;
    private LocalDateTime orderDateTime;
    private OrderStatus orderStatus;
    private String name;
    private String memo;

    private MemberDto memberDto;
    private List<OrderItemDto> orderItemDtos;
}
