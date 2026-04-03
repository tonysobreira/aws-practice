package com.example.monitoringdemo.controller;

import com.example.monitoringdemo.service.OrderService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@Validated
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
	public Map<String, Object> createOrder(@RequestParam @NotBlank String customer,
			@RequestParam(defaultValue = "1") @Min(1) int quantity) {
		return orderService.createOrder(customer, quantity);
	}

	@GetMapping("/health-check")
	public Map<String, Object> healthCheck() {
		return Map.of("status", "ok", "service", "monitoring-demo");
	}

	@GetMapping("/error-simulation")
	public Map<String, Object> simulateError() {
		return orderService.simulateError();
	}

	@GetMapping("/load-test")
	public Map<String, Object> simulateLoad(@RequestParam(defaultValue = "250") int milliseconds) {
		return orderService.simulateLoad(milliseconds);
	}

}
