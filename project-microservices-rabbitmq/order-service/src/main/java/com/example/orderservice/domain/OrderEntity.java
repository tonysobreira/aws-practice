package com.example.orderservice.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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
	private OrderStatus status;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	public OrderEntity() {
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
