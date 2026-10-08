package com.restaurant.inventory_service.presentation.dto;

import jakarta.validation.constraints.Min;

public record StockRequest(

        @Min(value = 1, message = "La cantidad debe ser mayor que cero")
        int quantity
) {}