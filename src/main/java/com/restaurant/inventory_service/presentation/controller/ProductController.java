package com.restaurant.inventory_service.presentation.controller;

import com.restaurant.inventory_service.application.service.ProductApplicationService;
import com.restaurant.inventory_service.domain.model.Product;
import com.restaurant.inventory_service.presentation.dto.CreateProductRequest;
import com.restaurant.inventory_service.presentation.dto.ProductResponse;
import com.restaurant.inventory_service.presentation.dto.StockRequest;
import com.restaurant.inventory_service.presentation.dto.UpdateProductRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.restaurant.inventory_service.presentation.dto.ProductAvailabilityResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductApplicationService productApplicationService;

    public ProductController(
            ProductApplicationService productApplicationService
    ) {
        this.productApplicationService = productApplicationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(
            @Valid @RequestBody CreateProductRequest request
    ) {
        Product product = productApplicationService.create(
                request.restaurantId(),
                request.name(),
                request.description(),
                request.price(),
                request.stock()
        );

        return ProductResponse.fromDomain(product);
    }

    @GetMapping
    public List<ProductResponse> findAll() {
        return productApplicationService.findAll()
                .stream()
                .map(ProductResponse::fromDomain)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductResponse findById(
            @PathVariable UUID id
    ) {
        return ProductResponse.fromDomain(
                productApplicationService.findById(id)
        );
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<ProductResponse> findByRestaurantId(
            @PathVariable UUID restaurantId
    ) {
        return productApplicationService
                .findByRestaurantId(restaurantId)
                .stream()
                .map(ProductResponse::fromDomain)
                .toList();
    }

    @PutMapping("/{id}")
    public ProductResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateProductRequest request
    ) {
        Product product = productApplicationService.update(
                id,
                request.name(),
                request.description(),
                request.price(),
                request.stock()
        );

        return ProductResponse.fromDomain(product);
    }

    @PatchMapping("/{id}/stock/increase")
    public ProductResponse increaseStock(
            @PathVariable UUID id,
            @Valid @RequestBody StockRequest request
    ) {
        return ProductResponse.fromDomain(
                productApplicationService.increaseStock(
                        id,
                        request.quantity()
                )
        );
    }

    @PatchMapping("/{id}/stock/decrease")
    public ProductResponse decreaseStock(
            @PathVariable UUID id,
            @Valid @RequestBody StockRequest request
    ) {
        return ProductResponse.fromDomain(
                productApplicationService.decreaseStock(
                        id,
                        request.quantity()
                )
        );
    }

    @PatchMapping("/{id}/activate")
    public ProductResponse activate(
            @PathVariable UUID id
    ) {
        return ProductResponse.fromDomain(
                productApplicationService.activate(id)
        );
    }

    @PatchMapping("/{id}/deactivate")
    public ProductResponse deactivate(
            @PathVariable UUID id
    ) {
        return ProductResponse.fromDomain(
                productApplicationService.deactivate(id)
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID id
    ) {
        productApplicationService.delete(id);
    }

    @GetMapping("/{id}/availability")
        public ResponseEntity<ProductAvailabilityResponse> checkAvailability(
                @PathVariable UUID id,
                @RequestParam int quantity
        ) {
        return ResponseEntity.ok(
                productApplicationService.checkAvailability(id, quantity)
        );
        }
        
}