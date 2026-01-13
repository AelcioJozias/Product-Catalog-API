package com.jozias.product.catalog.domain.entity;

import java.util.List;
import java.util.Objects;

public class ProductVariant {
    private Long id;
    private String type;
    private List<ProductVariantValue> values;

    public ProductVariant(Long id, String type, List<ProductVariantValue> values) {
        this.id = id;
        this.type = type;
        this.values = values;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ProductVariantValue> getValues() {
        return values;
    }

    public void setValues(List<ProductVariantValue> values) {
        this.values = values;
    }

    public void update(String type, List<ProductVariantValue> incomingValues) {
        this.type = type;
        updateValues(incomingValues);
    }

    private void updateValues(List<ProductVariantValue> incomingValues) {
        if (incomingValues == null || incomingValues.isEmpty()) {
            this.values.clear();
            return;
        }

        List<Long> incomingIds = incomingValues.stream()
                .map(ProductVariantValue::getId)
                .filter(Objects::nonNull)
                .toList();

        this.values.removeIf(existing -> existing.getId() != null && !incomingIds.contains(existing.getId()));

        for (ProductVariantValue incoming : incomingValues) {
            if (incoming.getId() == null) {
                this.values.add(incoming);
            } else {
                this.values.stream().filter(existing -> existing.getId().equals(incoming.getId())).findFirst()
                        .ifPresent(existing -> existing.update(incoming.getValue()));
            }
        }
    }
}
