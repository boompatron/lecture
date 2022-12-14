package com.kdt.lecture.order.controller;

import com.kdt.lecture.order.ApiResponse;
import com.kdt.lecture.order.dto.OrderDto;
import com.kdt.lecture.order.service.OrderService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import org.springframework.data.domain.Pageable;


@RestController
public class OrderController {
    @Autowired
    OrderService service;

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler(NotFoundException e){
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(InternalServerError.class)
    public ApiResponse<String> internalServerErrorHandler(InternalServerError e){
        return ApiResponse.fail(500, e.getMessage());
    }

    @PostMapping("/orders")
    public ApiResponse<String> save(@RequestBody OrderDto orderDto){
        String uuid = service.save(orderDto);
        return ApiResponse.ok(uuid);
    }

    @GetMapping("/orders/{uuid}")
    public ApiResponse<OrderDto> getOne(@PathVariable String uuid) throws NotFoundException {
        OrderDto one = service.findOne(uuid);
        return ApiResponse.ok(one);
    }

    @GetMapping("/orders")
    public ApiResponse<Page<OrderDto>> getAll(Pageable pageable){
        Page<OrderDto> all = service.findAll(pageable);
        return ApiResponse.ok(all);
    }
}
