package com.example.orderservice.dto;

import com.example.orderservice.domain.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderResponse {

	private Long id;

	private String customerName;

	private String customerEmail;

	private String product;

	private Integer quantity;

	private BigDecimal amount;

	private OrderStatus status;

	private LocalDateTime createdAt;

	public OrderResponse() {
	}

	public OrderResponse(Long id, String customerName, String customerEmail, String product, Integer quantity,
			BigDecimal amount, OrderStatus status, LocalDateTime createdAt) {
		this.id = id;
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.product = product;
		this.quantity = quantity;
		this.amount = amount;
		this.status = status;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
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

	public OrderStatus getStatus() {
		return status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
