package com.example.orderservice;

import com.example.orderservice.domain.OrderEntity;
import com.example.orderservice.domain.OrderStatus;
import com.example.orderservice.dto.CreateOrderRequest;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.messaging.OrderEventPublisher;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {
    @Test
    void shouldCreateOrderAndPublishEvent() {
        OrderRepository repository = mock(OrderRepository.class);
        OrderEventPublisher publisher = mock(OrderEventPublisher.class);
        OrderService service = new OrderService(repository, publisher);

        CreateOrderRequest request = new CreateOrderRequest();
        request.setCustomerName("Tony");
        request.setCustomerEmail("tony@example.com");
        request.setProduct("Notebook");
        request.setQuantity(1);
        request.setAmount(new BigDecimal("4500.00"));

        when(repository.save(any(OrderEntity.class))).thenAnswer(invocation -> {
            OrderEntity entity = invocation.getArgument(0);
            entity.setId(1L);
            entity.setCreatedAt(LocalDateTime.now());
            entity.setStatus(OrderStatus.CREATED);
            return entity;
        });

        OrderResponse response = service.createOrder(request);
        assertNotNull(response.getId());
        assertEquals("Tony", response.getCustomerName());
        assertEquals(OrderStatus.CREATED, response.getStatus());
        verify(repository, times(1)).save(any(OrderEntity.class));
        verify(publisher, times(1)).publish(any());
    }
}
