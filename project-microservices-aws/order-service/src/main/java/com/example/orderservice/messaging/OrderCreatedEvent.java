package com.example.orderservice.messaging;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderCreatedEvent {

	private Long orderId;

	private String customerName;

	private String customerEmail;

	private String product;

	private Integer quantity;

	private BigDecimal amount;

	private String status;

	private LocalDateTime createdAt;

	public OrderCreatedEvent() {
	}

	public Long getOrderId() {
		return orderId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public String getProduct() {
		return product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public String getStatus() {
		return status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
