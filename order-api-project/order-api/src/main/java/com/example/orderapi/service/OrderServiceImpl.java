package com.example.orderapi.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.orderapi.dto.CreateOrderItemRequest;
import com.example.orderapi.dto.CreateOrderRequest;
import com.example.orderapi.dto.OrderItemResponse;
import com.example.orderapi.dto.OrderResponse;
import com.example.orderapi.dto.UpdateOrderStatusRequest;
import com.example.orderapi.entity.Order;
import com.example.orderapi.entity.OrderItem;
import com.example.orderapi.entity.OrderStatus;
import com.example.orderapi.entity.Product;
import com.example.orderapi.exception.ResourceNotFoundException;
import com.example.orderapi.repository.CustomerRepository;
import com.example.orderapi.repository.OrderItemRepository;
import com.example.orderapi.repository.OrderRepository;
import com.example.orderapi.repository.ProductRepository;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final CustomerRepository customerRepository;
	private final ProductRepository productRepository;

	public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
			CustomerRepository customerRepository, ProductRepository productRepository) {
		this.orderRepository = orderRepository;
		this.orderItemRepository = orderItemRepository;
		this.customerRepository = customerRepository;
		this.productRepository = productRepository;
	}

	@Override
	@Transactional
	public OrderResponse createOrder(CreateOrderRequest request) {
		log.info("Creating order for customerId={}", request.customerId());

		if (!customerRepository.existsById(request.customerId())) {
			throw new ResourceNotFoundException("Customer not found with id=" + request.customerId());
		}

		List<Long> productIds = request.items().stream().map(CreateOrderItemRequest::productId).toList();
		Map<Long, Product> productsById = new HashMap<>();
		productRepository.findAllById(productIds).forEach(product -> productsById.put(product.getId(), product));

		for (Long productId : productIds) {
			if (!productsById.containsKey(productId)) {
				throw new ResourceNotFoundException("Product not found with id=" + productId);
			}
		}

		BigDecimal totalAmount = request.items().stream().map(item -> {
			Product product = productsById.get(item.productId());
			return product.getPrice().multiply(BigDecimal.valueOf(item.quantity()));
		}).reduce(BigDecimal.ZERO, BigDecimal::add);

		Order o = new Order(request.customerId(), OrderStatus.CREATED, totalAmount, LocalDateTime.now());

		Order order = orderRepository.save(o);

		List<OrderItem> items = request.items().stream().map(item -> {
			Product product = productsById.get(item.productId());
			return new OrderItem(order.getId(), item.productId(), item.quantity(), product.getPrice());
		}).toList();

		orderItemRepository.saveAll(items);
		log.info("Order created with id={} and totalAmount={}", order.getId(), order.getTotalAmount());
		return toResponse(order, items);
	}

	@Override
	@Transactional(readOnly = true)
	public OrderResponse findById(Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found with id=" + id));
		List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());
		return toResponse(order, items);
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrderResponse> findAll() {
		return orderRepository.findAll().stream()
				.map(order -> toResponse(order, orderItemRepository.findByOrderId(order.getId()))).toList();
	}

	@Override
	@Transactional
	public OrderResponse updateStatus(Long id, UpdateOrderStatusRequest request) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found with id=" + id));

		order.setStatus(request.status());
		orderRepository.save(order);
		log.info("Order id={} status updated to {}", order.getId(), order.getStatus());
		return toResponse(order, orderItemRepository.findByOrderId(order.getId()));
	}

	private OrderResponse toResponse(Order order, List<OrderItem> items) {
		List<OrderItemResponse> itemResponses = items
				.stream().map(item -> new OrderItemResponse(item.getProductId(), item.getQuantity(),
						item.getUnitPrice(), item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()))))
				.toList();

		return new OrderResponse(order.getId(), order.getCustomerId(), order.getStatus(), order.getTotalAmount(),
				order.getCreatedAt(), itemResponses);
	}

}
