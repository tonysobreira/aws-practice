package com.example.monitoringdemo.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderService {

	private static final Logger log = LoggerFactory.getLogger(OrderService.class);

	private final Counter orderCreatedCounter;

	private final Counter orderFailedCounter;

	private final Timer businessTimer;

	private final AtomicInteger demoQueueSizeGauge;

	public OrderService(Counter orderCreatedCounter, Counter orderFailedCounter, Timer businessTimer,
			AtomicInteger demoQueueSizeGauge) {
		this.orderCreatedCounter = orderCreatedCounter;
		this.orderFailedCounter = orderFailedCounter;
		this.businessTimer = businessTimer;
		this.demoQueueSizeGauge = demoQueueSizeGauge;
	}

	public Map<String, Object> createOrder(String customer, int quantity) {
		return businessTimer.record(() -> {
			demoQueueSizeGauge.incrementAndGet();
			try {
				sleep(120);
				String orderId = UUID.randomUUID().toString();
				orderCreatedCounter.increment();
				log.info("Order created successfully. orderId={}, customer={}, quantity={}", orderId, customer,
						quantity);

				Map<String, Object> response = new LinkedHashMap<>();
				response.put("orderId", orderId);
				response.put("customer", customer);
				response.put("quantity", quantity);
				response.put("status", "CREATED");
				response.put("createdAt", Instant.now().toString());
				return response;
			} finally {
				demoQueueSizeGauge.decrementAndGet();
			}
		});
	}

	public Map<String, Object> simulateError() {
		orderFailedCounter.increment();
		log.error("Simulated application error triggered");
		throw new IllegalStateException("Erro simulado para gerar log e metrica");
	}

	public Map<String, Object> simulateLoad(int milliseconds) {
		return businessTimer.record(() -> {
			demoQueueSizeGauge.incrementAndGet();
			try {
				sleep(milliseconds);
				log.info("Load simulation executed. durationMs={}", milliseconds);
				return Map.of("message", "Load simulation finished", "durationMs", milliseconds);
			} finally {
				demoQueueSizeGauge.decrementAndGet();
			}
		});
	}

	private void sleep(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
