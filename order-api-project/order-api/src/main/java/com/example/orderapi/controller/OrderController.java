package com.example.orderapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.orderapi.dto.CreateOrderRequest;
import com.example.orderapi.dto.OrderResponse;
import com.example.orderapi.dto.UpdateOrderStatusRequest;
import com.example.orderapi.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrderResponse create(@Valid @RequestBody CreateOrderRequest request) {
		return orderService.createOrder(request);
	}

	@GetMapping("/{id}")
	public OrderResponse findById(@PathVariable Long id) {
		return orderService.findById(id);
	}

	@GetMapping
	public List<OrderResponse> findAll() {
		return orderService.findAll();
	}

	@PatchMapping("/{id}/status")
	public OrderResponse updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateOrderStatusRequest request) {
		return orderService.updateStatus(id, request);
	}

}
