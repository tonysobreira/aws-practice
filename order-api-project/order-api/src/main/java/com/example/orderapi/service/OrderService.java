package com.example.orderapi.service;

import com.example.orderapi.dto.CreateOrderRequest;
import com.example.orderapi.dto.OrderResponse;
import com.example.orderapi.dto.UpdateOrderStatusRequest;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request);
    OrderResponse findById(Long id);
    List<OrderResponse> findAll();
    OrderResponse updateStatus(Long id, UpdateOrderStatusRequest request);
}
