package com.restaurant.inventory_service.presentation.dto;

import com.restaurant.inventory_service.domain.model.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        UUID restaurantId,
        String name,
        String description,
        BigDecimal price,
        int stock,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static ProductResponse fromDomain(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getRestaurantId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.isActive(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}