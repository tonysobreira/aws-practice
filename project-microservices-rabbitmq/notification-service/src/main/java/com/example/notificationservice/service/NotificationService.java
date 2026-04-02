package com.example.notificationservice.service;

import com.example.notificationservice.domain.NotificationEntity;
import com.example.notificationservice.domain.NotificationStatus;
import com.example.notificationservice.dto.NotificationResponse;
import com.example.notificationservice.messaging.OrderCreatedEvent;
import com.example.notificationservice.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

	private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

	private final NotificationRepository notificationRepository;

	public NotificationService(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	public void processOrderCreatedEvent(OrderCreatedEvent event) {
		log.info("Processing order created event. orderId={}", event.getOrderId());
		NotificationEntity entity = new NotificationEntity();
		entity.setOrderId(event.getOrderId());
		entity.setCustomerName(event.getCustomerName());
		entity.setCustomerEmail(event.getCustomerEmail());
		entity.setProduct(event.getProduct());
		entity.setQuantity(event.getQuantity());
		entity.setAmount(event.getAmount());
		entity.setStatus(NotificationStatus.PROCESSED);
		entity.setReceivedAt(LocalDateTime.now());
		notificationRepository.save(entity);
		log.info("Notification saved successfully for orderId={}", event.getOrderId());
	}

	public List<NotificationResponse> findAll() {
		return notificationRepository.findAll().stream().map(this::toResponse).toList();
	}

	private NotificationResponse toResponse(NotificationEntity entity) {
		return new NotificationResponse(entity.getId(), entity.getOrderId(), entity.getCustomerName(),
				entity.getCustomerEmail(), entity.getProduct(), entity.getQuantity(), entity.getAmount(),
				entity.getStatus(), entity.getReceivedAt());
	}

}
