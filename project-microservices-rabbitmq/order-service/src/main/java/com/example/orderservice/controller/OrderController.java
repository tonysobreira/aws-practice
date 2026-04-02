package com.example.orderservice.controller;

import com.example.orderservice.dto.CreateOrderRequest;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.dto.UpdateOrderStatusRequest;
import com.example.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
		return orderService.createOrder(request);
	}

	@GetMapping
	public List<OrderResponse> listOrders() {
		return orderService.findAll();
	}

	@GetMapping("/{id}")
	public OrderResponse getOrderById(@PathVariable Long id) {
		return orderService.findById(id);
	}

	@PatchMapping("/{id}/status")
	public OrderResponse updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateOrderStatusRequest request) {
		return orderService.updateStatus(id, request);
	}

}
