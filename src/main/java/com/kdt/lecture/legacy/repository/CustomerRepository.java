package com.kdt.lecture.legacy.repository;

import com.kdt.lecture.legacy.repository.domain.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

}
