package com.example.orderservice.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Component
public class OrderEventPublisher {

	private static final Logger log = LoggerFactory.getLogger(OrderEventPublisher.class);

	private final SqsClient sqsClient;

	private final ObjectMapper objectMapper;

	private final String queueUrl;

	public OrderEventPublisher(SqsClient sqsClient, ObjectMapper objectMapper,
			@Value("${aws.sqs.queue-url}") String queueUrl) {
		this.sqsClient = sqsClient;
		this.objectMapper = objectMapper;
		this.queueUrl = queueUrl;
	}

	public void publish(OrderCreatedEvent event) {
		try {
			String messageBody = objectMapper.writeValueAsString(event);

			SendMessageRequest request = SendMessageRequest.builder().queueUrl(queueUrl).messageBody(messageBody)
					.build();

			sqsClient.sendMessage(request);
			log.info("Order event published to SQS. orderId={}", event.getOrderId());
		} catch (JsonProcessingException e) {
			throw new IllegalStateException("Erro ao serializar OrderCreatedEvent", e);
		}
	}

}
