package com.example.orderapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequest(
        @NotNull(message = "Customer id is required") Long customerId,
        @Valid @NotEmpty(message = "At least one item is required") List<CreateOrderItemRequest> items
) {
}
