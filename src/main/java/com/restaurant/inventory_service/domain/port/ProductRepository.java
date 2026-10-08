package com.restaurant.inventory_service.domain.port;

import com.restaurant.inventory_service.domain.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    Product save(Product product);

    List<Product> findAll();

    Optional<Product> findById(UUID id);

    List<Product> findByRestaurantId(UUID restaurantId);

    void deleteById(UUID id);
}