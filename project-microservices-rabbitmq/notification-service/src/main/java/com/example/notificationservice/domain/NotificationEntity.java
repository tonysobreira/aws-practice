package com.example.notificationservice.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class NotificationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long orderId;

	@Column(nullable = false)
	private String customerName;

	@Column(nullable = false)
	private String customerEmail;

	@Column(nullable = false)
	private String product;

	@Column(nullable = false)
	private Integer quantity;

	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal amount;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private NotificationStatus status;

	@Column(nullable = false)
	private LocalDateTime receivedAt;

	public NotificationEntity() {
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
