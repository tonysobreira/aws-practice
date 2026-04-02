package com.example.notificationservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public static final String EXCHANGE_NAME = "orders.exchange";

	public static final String ROUTING_KEY = "order.created";

	public static final String QUEUE_NAME = "orders.created.queue";

	@Bean
	public TopicExchange ordersExchange() {
		return new TopicExchange(EXCHANGE_NAME);
	}

	@Bean
	public Queue ordersQueue() {
		return new Queue(QUEUE_NAME, true);
	}

	@Bean
	public Binding ordersBinding(Queue ordersQueue, TopicExchange ordersExchange) {
		return BindingBuilder.bind(ordersQueue).to(ordersExchange).with(ROUTING_KEY);
	}

	@Bean
	public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

}
