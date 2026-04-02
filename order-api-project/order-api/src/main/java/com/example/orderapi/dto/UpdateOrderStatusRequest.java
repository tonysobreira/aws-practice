package com.example.orderapi.dto;

import com.example.orderapi.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatusRequest(
        @NotNull(message = "Status is required") OrderStatus status
) {
}
