package com.example.notificationservice.messaging;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.notificationservice.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

@Component
public class SqsOrderConsumer {

	private static final Logger log = LoggerFactory.getLogger(SqsOrderConsumer.class);

	private final SqsClient sqsClient;
	private final ObjectMapper objectMapper;
	private final NotificationService notificationService;
	private final String queueUrl;

	public SqsOrderConsumer(SqsClient sqsClient, ObjectMapper objectMapper, NotificationService notificationService,
			@Value("${aws.sqs.queue-url}") String queueUrl) {
		this.sqsClient = sqsClient;
		this.objectMapper = objectMapper;
		this.notificationService = notificationService;
		this.queueUrl = queueUrl;
	}

	@Scheduled(fixedDelay = 5000)
	public void pollMessages() {
		ReceiveMessageRequest request = ReceiveMessageRequest.builder().queueUrl(queueUrl).maxNumberOfMessages(10)
				.waitTimeSeconds(10).build();

		List<Message> messages = sqsClient.receiveMessage(request).messages();

		if (messages.isEmpty()) {
			return;
		}

		log.info("Received {} message(s) from SQS", messages.size());

		for (Message message : messages) {
			try {
				OrderCreatedEvent event = objectMapper.readValue(message.body(), OrderCreatedEvent.class);

				notificationService.processOrderCreatedEvent(event);

				sqsClient.deleteMessage(DeleteMessageRequest.builder().queueUrl(queueUrl)
						.receiptHandle(message.receiptHandle()).build());

				log.info("Message processed and deleted. messageId={}", message.messageId());
			} catch (Exception e) {
				log.error("Erro ao processar mensagem SQS. body={}", message.body(), e);
			}
		}
	}

}