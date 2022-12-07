package com.kdt.lecture.domain.parent;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode // 필수
@NoArgsConstructor // 필수
@AllArgsConstructor // 편의상
public class ParentId implements Serializable {
    private String id1;
    private String id2;
}
