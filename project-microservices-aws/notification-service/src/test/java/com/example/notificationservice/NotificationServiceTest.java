package com.example.notificationservice;

import com.example.notificationservice.messaging.OrderCreatedEvent;
import com.example.notificationservice.repository.NotificationRepository;
import com.example.notificationservice.service.NotificationService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

public class NotificationServiceTest {
    @Test
    void shouldSaveNotificationWhenEventIsReceived() {
        NotificationRepository repository = mock(NotificationRepository.class);
        NotificationService service = new NotificationService(repository);

        OrderCreatedEvent event = new OrderCreatedEvent();
        event.setOrderId(10L);
        event.setCustomerName("Tony");
        event.setCustomerEmail("tony@example.com");
        event.setProduct("Curso AWS");
        event.setQuantity(1);
        event.setAmount(new BigDecimal("99.90"));

        service.processOrderCreatedEvent(event);
        verify(repository, times(1)).save(any());
    }
}
