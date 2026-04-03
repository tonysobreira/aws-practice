package com.example.monitoringdemo;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class MonitoringDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringDemoApplication.class, args);
	}

	@Bean
	public AtomicInteger demoQueueSizeGauge(MeterRegistry registry) {
		AtomicInteger queueSize = new AtomicInteger(0);
		registry.gauge("demo_queue_size", queueSize);
		return queueSize;
	}

	@Bean
	public Counter orderCreatedCounter(MeterRegistry registry) {
		return Counter.builder("demo_created_orders").description("Total de pedidos criados").register(registry);
	}

	@Bean
	public Counter orderFailedCounter(MeterRegistry registry) {
		return Counter.builder("demo_failed_orders").description("Total de falhas simuladas").register(registry);
	}

	@Bean
	public Timer businessTimer(MeterRegistry registry) {
		return Timer.builder("demo_business_operation_seconds").description("Tempo das operacoes de negocio")
				.publishPercentileHistogram().register(registry);
	}

}
