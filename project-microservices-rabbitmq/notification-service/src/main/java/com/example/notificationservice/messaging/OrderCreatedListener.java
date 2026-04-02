package com.example.notificationservice.messaging;

import com.example.notificationservice.config.RabbitMQConfig;
import com.example.notificationservice.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

	private static final Logger log = LoggerFactory.getLogger(OrderCreatedListener.class);

	private final NotificationService notificationService;

	public OrderCreatedListener(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
	public void consume(OrderCreatedEvent event) {
		log.info("Message received from queue. orderId={}", event.getOrderId());
		notificationService.processOrderCreatedEvent(event);
	}

}
