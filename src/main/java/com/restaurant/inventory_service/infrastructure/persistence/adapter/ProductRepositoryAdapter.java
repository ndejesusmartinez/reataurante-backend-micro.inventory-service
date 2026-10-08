package com.restaurant.inventory_service.infrastructure.persistence.adapter;

import com.restaurant.inventory_service.domain.model.Product;
import com.restaurant.inventory_service.domain.port.ProductRepository;
import com.restaurant.inventory_service.infrastructure.persistence.entity.ProductEntity;
import com.restaurant.inventory_service.infrastructure.persistence.repository.ProductJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProductRepositoryAdapter implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    public ProductRepositoryAdapter(
            ProductJpaRepository productJpaRepository
    ) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = toEntity(product);

        ProductEntity savedEntity =
                productJpaRepository.save(entity);

        return toDomain(savedEntity);
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return productJpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<Product> findByRestaurantId(UUID restaurantId) {
        return productJpaRepository
                .findByRestaurantId(restaurantId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        productJpaRepository.deleteById(id);
    }

    private ProductEntity toEntity(Product product) {
        return new ProductEntity(
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

    private Product toDomain(ProductEntity entity) {
        return new Product(
                entity.getId(),
                entity.getRestaurantId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStock(),
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}