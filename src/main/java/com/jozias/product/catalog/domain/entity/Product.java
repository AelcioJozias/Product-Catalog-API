package com.jozias.product.catalog.domain.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.jozias.product.catalog.domain.exception.ProductInstanceInvalidException;
import com.jozias.product.catalog.domain.util.StringUtils;

public class Product {

    private final Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer availableQuantity;
    private Condition condition;
    private String category;
    private final List<ProductVariant> variants;
    private final Seller seller;

    @SuppressWarnings("java:S107")
    public Product(Long id, String name, String description, BigDecimal price, Integer availableQuantity,
            Condition condition, String category, List<ProductVariant> variants, Seller seller) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.condition = condition;
        this.category = category;
        this.variants = variants != null ? variants : new ArrayList<>();
        this.seller = seller;

        validateState();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public Condition getCondition() {
        return condition;
    }

    public String getCategory() {
        return category;
    }

    public List<ProductVariant> getVariants() {
        return variants;
    }

    public void addVariant(ProductVariant variant) {
        if (variant != null) {
            this.variants.add(variant);
        }
    }

    public void removeVariant(ProductVariant variant) {
        if (variant != null) {
            this.variants.remove(variant);
        }
    }

    public Seller getSeller() {
        return seller;
    }

    public void update(String name, String description, BigDecimal price, Integer availableQuantity,
            Condition condition, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.condition = condition;
        this.category = category;

        validateState();
    }

    public void updateVariants(List<ProductVariant> incomingVariants) {
        if (incomingVariants == null || incomingVariants.isEmpty()) {
            this.variants.clear();
            return;
        }

        List<Long> incomingIds = incomingVariants.stream()
                .map(ProductVariant::getId)
                .filter(id -> id != null)
                .toList();

        this.variants.removeIf(
                existingVariant -> existingVariant.getId() != null && !incomingIds.contains(existingVariant.getId()));

        for (ProductVariant incoming : incomingVariants) {
            if (incoming.getId() == null) {
                this.addVariant(incoming);
            } else {
                this.variants.stream()
                        .filter(existing -> existing.getId().equals(incoming.getId()))
                        .findFirst()
                        .ifPresent(existing -> existing.update(incoming.getType(), incoming.getValues()));
            }
        }
    }

    private void validateState() {
        if (!StringUtils.isAValidString(this.name, 3)) {
            throw new ProductInstanceInvalidException(
                    "The field 'name' in product is invalid. The name must be not null and have at least 3 characters");
        }

        if (!StringUtils.isAValidString(this.description, 10)) {
            throw new ProductInstanceInvalidException(
                    "The field 'description' in product is invalid. The description must be not null and have at least 10 characters");
        }

        if (this.price == null || this.price.signum() < 0) {
            throw new ProductInstanceInvalidException(
                    "The field 'price' in product is invalid {}. The price must be greater than zero");
        }

        if (this.condition == null) {
            throw new ProductInstanceInvalidException(
                    "The field 'condition' in product is invalid {}. The condition must be not null");
        }

        if (!StringUtils.isAValidString(this.category, 3)) {
            throw new ProductInstanceInvalidException("The field 'category' in product is invalid {}");
        }

        if (this.seller == null) {
            throw new ProductInstanceInvalidException(
                    "The field 'seller' in product is invalid {}. The seller must be not null");
        }
    }

}
