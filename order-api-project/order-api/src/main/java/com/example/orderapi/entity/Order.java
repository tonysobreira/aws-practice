package com.example.orderapi.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long customerId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus status;

	@Column(nullable = false, precision = 15, scale = 2)
	private BigDecimal totalAmount;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	public Order() {
	}

	public Order(Long id, Long customerId, OrderStatus status, BigDecimal totalAmount, LocalDateTime createdAt) {
		this.id = id;
		this.customerId = customerId;
		this.status = status;
		this.totalAmount = totalAmount;
		this.createdAt = createdAt;
	}

	public Order(Long customerId, OrderStatus status, BigDecimal totalAmount, LocalDateTime createdAt) {
		this.customerId = customerId;
		this.status = status;
		this.totalAmount = totalAmount;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
