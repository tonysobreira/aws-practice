package com.example.orderservice.service;

import com.example.orderservice.domain.OrderEntity;
import com.example.orderservice.domain.OrderStatus;
import com.example.orderservice.dto.CreateOrderRequest;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.dto.UpdateOrderStatusRequest;
import com.example.orderservice.exception.ResourceNotFoundException;
import com.example.orderservice.messaging.OrderCreatedEvent;
import com.example.orderservice.messaging.OrderEventPublisher;
import com.example.orderservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

	private static final Logger log = LoggerFactory.getLogger(OrderService.class);

	private final OrderRepository orderRepository;

	private final OrderEventPublisher orderEventPublisher;

	public OrderService(OrderRepository orderRepository, OrderEventPublisher orderEventPublisher) {
		this.orderRepository = orderRepository;
		this.orderEventPublisher = orderEventPublisher;
	}

	public OrderResponse createOrder(CreateOrderRequest request) {
		log.info("Creating order for customerEmail={}", request.getCustomerEmail());
		OrderEntity entity = new OrderEntity();
		entity.setCustomerName(request.getCustomerName());
		entity.setCustomerEmail(request.getCustomerEmail());
		entity.setProduct(request.getProduct());
		entity.setQuantity(request.getQuantity());
		entity.setAmount(request.getAmount());
		entity.setStatus(OrderStatus.CREATED);
		entity.setCreatedAt(LocalDateTime.now());
		OrderEntity saved = orderRepository.save(entity);
		log.info("Order created successfully. orderId={}", saved.getId());

		OrderCreatedEvent event = new OrderCreatedEvent();
		event.setOrderId(saved.getId());
		event.setCustomerName(saved.getCustomerName());
		event.setCustomerEmail(saved.getCustomerEmail());
		event.setProduct(saved.getProduct());
		event.setQuantity(saved.getQuantity());
		event.setAmount(saved.getAmount());
		event.setStatus(saved.getStatus().name());
		event.setCreatedAt(saved.getCreatedAt());
		orderEventPublisher.publish(event);
		return toResponse(saved);
	}

	public List<OrderResponse> findAll() {
		return orderRepository.findAll().stream().map(this::toResponse).toList();
	}

	public OrderResponse findById(Long id) {
		return toResponse(findEntityById(id));
	}

	public OrderResponse updateStatus(Long id, UpdateOrderStatusRequest request) {
		OrderEntity entity = findEntityById(id);
		entity.setStatus(request.getStatus());
		OrderEntity saved = orderRepository.save(entity);
		log.info("Order status updated. orderId={}, status={}", saved.getId(), saved.getStatus());
		return toResponse(saved);
	}

	private OrderEntity findEntityById(Long id) {
		return orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found. id=" + id));
	}

	private OrderResponse toResponse(OrderEntity entity) {
		return new OrderResponse(entity.getId(), entity.getCustomerName(), entity.getCustomerEmail(),
				entity.getProduct(), entity.getQuantity(), entity.getAmount(), entity.getStatus(),
				entity.getCreatedAt());
	}

}
