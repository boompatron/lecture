package com.kdt.lecture.legacy.repository;

import com.kdt.lecture.legacy.repository.domain.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper {
    void save(Customer customer);
    Customer findById(long id);

}
