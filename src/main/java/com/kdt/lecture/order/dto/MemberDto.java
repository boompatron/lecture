package com.kdt.lecture.order.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String name;
    private String nickName;
    private int age;
    private String address;
    private String description;
}
