package com.example.orderapi.dto;

import java.math.BigDecimal;

public record OrderItemResponse(Long productId, Integer quantity, BigDecimal unitPrice, BigDecimal subtotal) {
}
