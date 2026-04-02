package com.example.orderservice.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class CreateOrderRequest {

	@NotBlank
	private String customerName;

	@NotBlank
	@Email
	private String customerEmail;

	@NotBlank
	private String product;

	@NotNull
	@Positive
	private Integer quantity;

	@NotNull
	@DecimalMin(value = "0.01")
	private BigDecimal amount;

	public CreateOrderRequest() {
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

}
