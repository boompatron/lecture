package com.kdt.lecture.order.service;

import com.kdt.lecture.domain.order.OrderRepository;
import com.kdt.lecture.order.converter.OrderConverter;
import com.kdt.lecture.order.dto.OrderDto;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.kdt.lecture.domain.order.Order;

import javax.transaction.Transactional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderConverter converter;

    @Transactional
    public String save(OrderDto dto){
        // transaction.begin()
        // 1. -> dto를 entity 로 변환(준영속)
        Order order = converter.convertOrder(dto);
        // 2. repo에 저장해서 영속화
        Order entity = repository.save(order);
        // 3. 반환
        return entity.getUuid();
        // transaction.commit()
        // -> @transactional

    }

    @Transactional
    public OrderDto findOne(String uuid) throws NotFoundException {
        // 1. 조회를 위한 키값 인자로 받기
        // 2. order repo, findbyid -> 조회 -> 영속화된 데이터
        return repository.findById(uuid)
                .map(converter::convertOrderDto)
                .orElseThrow(() -> new NotFoundException("entity not found!!"));
        // 3. entity -> dto : transaction 밖으로 나갔는데 쿼리가 나갈 수 있어서 dto로 변환하는게 좋음
    }

    @Transactional
    public Page<OrderDto> findAll(Pageable pageable){
        return repository.findAll(pageable).map(converter::convertOrderDto);
    }
}
