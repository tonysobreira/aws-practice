package com.example.orderapi.service;

import com.example.orderapi.dto.CreateOrderItemRequest;
import com.example.orderapi.dto.CreateOrderRequest;
import com.example.orderapi.dto.OrderResponse;
import com.example.orderapi.dto.UpdateOrderStatusRequest;
import com.example.orderapi.entity.*;
import com.example.orderapi.exception.ResourceNotFoundException;
import com.example.orderapi.repository.CustomerRepository;
import com.example.orderapi.repository.OrderItemRepository;
import com.example.orderapi.repository.OrderRepository;
import com.example.orderapi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

	@Mock
	private OrderRepository orderRepository;
	@Mock
	private OrderItemRepository orderItemRepository;
	@Mock
	private CustomerRepository customerRepository;
	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private OrderServiceImpl orderService;

	private Product notebook;

	@BeforeEach
	void setUp() {
		notebook = new Product(10L, "Notebook", new BigDecimal("100.00"));
	}

	@Test
	void shouldCreateOrderSuccessfully() {
		CreateOrderRequest request = new CreateOrderRequest(1L, List.of(new CreateOrderItemRequest(10L, 2)));

		when(customerRepository.existsById(1L)).thenReturn(true);
		when(productRepository.findAllById(List.of(10L))).thenReturn(List.of(notebook));
		when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
			Order order = invocation.getArgument(0);
			order.setId(99L);
			return order;
		});
		when(orderItemRepository.saveAll(any())).thenAnswer(invocation -> invocation.getArgument(0));

		OrderResponse response = orderService.createOrder(request);

		assertNotNull(response);
		assertEquals(99L, response.id());
		assertEquals(new BigDecimal("200.00"), response.totalAmount());
		assertEquals(OrderStatus.CREATED, response.status());
		assertEquals(1, response.items().size());

		ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
		verify(orderRepository).save(orderCaptor.capture());
		assertEquals(new BigDecimal("200.00"), orderCaptor.getValue().getTotalAmount());
	}

	@Test
	void shouldThrowWhenCustomerDoesNotExist() {
		CreateOrderRequest request = new CreateOrderRequest(1L, List.of(new CreateOrderItemRequest(10L, 2)));

		when(customerRepository.existsById(1L)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> orderService.createOrder(request));
		verify(orderRepository, never()).save(any(Order.class));
	}

	@Test
	void shouldThrowWhenProductDoesNotExist() {
		CreateOrderRequest request = new CreateOrderRequest(1L, List.of(new CreateOrderItemRequest(10L, 2)));

		when(customerRepository.existsById(1L)).thenReturn(true);
		when(productRepository.findAllById(List.of(10L))).thenReturn(List.of());

		assertThrows(ResourceNotFoundException.class, () -> orderService.createOrder(request));
		verify(orderRepository, never()).save(any(Order.class));
	}

	@Test
	void shouldUpdateStatus() {
		Order order = new Order(99L, 1L, OrderStatus.CREATED, new BigDecimal("100.00"), LocalDateTime.now());
		OrderItem item = new OrderItem(1L, 99L, 10L, 1, new BigDecimal("100.00"));

		when(orderRepository.findById(99L)).thenReturn(Optional.of(order));
		when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
		when(orderItemRepository.findByOrderId(99L)).thenReturn(List.of(item));

		OrderResponse response = orderService.updateStatus(99L, new UpdateOrderStatusRequest(OrderStatus.PAID));

		assertEquals(OrderStatus.PAID, response.status());
	}

}
