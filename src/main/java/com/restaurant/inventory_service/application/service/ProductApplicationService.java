package com.restaurant.inventory_service.application.service;

import com.restaurant.inventory_service.domain.model.Product;
import com.restaurant.inventory_service.domain.port.ProductRepository;
import org.springframework.stereotype.Service;
import com.restaurant.inventory_service.presentation.dto.ProductAvailabilityResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ProductApplicationService {

    private final ProductRepository productRepository;

    public ProductApplicationService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(
            UUID restaurantId,
            String name,
            String description,
            BigDecimal price,
            int stock
    ) {
        Product product = Product.create(
                restaurantId,
                name,
                description,
                price,
                stock
        );

        return productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Producto no encontrado: " + id
                        )
                );
    }

    public List<Product> findByRestaurantId(UUID restaurantId) {
        return productRepository.findByRestaurantId(restaurantId);
    }

    public Product update(
            UUID id,
            String name,
            String description,
            BigDecimal price,
            int stock
    ) {
        Product product = findById(id);

        product.update(
                name,
                description,
                price,
                stock
        );

        return productRepository.save(product);
    }

    public Product increaseStock(UUID id, int quantity) {
        Product product = findById(id);

        product.increaseStock(quantity);

        return productRepository.save(product);
    }

    public Product decreaseStock(UUID id, int quantity) {
        Product product = findById(id);

        product.decreaseStock(quantity);

        return productRepository.save(product);
    }

    public Product activate(UUID id) {
        Product product = findById(id);

        product.activate();

        return productRepository.save(product);
    }

    public Product deactivate(UUID id) {
        Product product = findById(id);

        product.deactivate();

        return productRepository.save(product);
    }

    public void delete(UUID id) {
        Product product = findById(id);

        productRepository.deleteById(product.getId());
    }

    public ProductAvailabilityResponse checkAvailability(UUID productId, int quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Producto no encontrado: " + productId
                ));

        boolean available = product.getStock() >= quantity;

        return new ProductAvailabilityResponse(
                product.getId(),
                available,
                product.getStock()
        );
    }
}