package com.example.notificationservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.notificationservice.domain.NotificationStatus;

public class NotificationResponse {

	private Long id;

	private Long orderId;

	private String customerName;

	private String customerEmail;

	private String product;

	private Integer quantity;

	private BigDecimal amount;

	private NotificationStatus status;

	private LocalDateTime receivedAt;

	public NotificationResponse() {
	}

	public NotificationResponse(Long id, Long orderId, String customerName, String customerEmail, String product,
			Integer quantity, BigDecimal amount, NotificationStatus status, LocalDateTime receivedAt) {
		this.id = id;
		this.orderId = orderId;
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.product = product;
		this.quantity = quantity;
		this.amount = amount;
		this.status = status;
		this.receivedAt = receivedAt;
	}

	public Long getId() {
		return id;
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

	public NotificationStatus getStatus() {
		return status;
	}

	public LocalDateTime getReceivedAt() {
		return receivedAt;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setStatus(NotificationStatus status) {
		this.status = status;
	}

	public void setReceivedAt(LocalDateTime receivedAt) {
		this.receivedAt = receivedAt;
	}

}
