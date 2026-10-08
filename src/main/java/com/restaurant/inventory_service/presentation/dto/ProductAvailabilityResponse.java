package com.restaurant.inventory_service.presentation.dto;

import java.util.UUID;

public record ProductAvailabilityResponse(
        UUID productId,
        boolean available,
        int stock
) {
}