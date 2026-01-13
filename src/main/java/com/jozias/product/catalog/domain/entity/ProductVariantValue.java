package com.jozias.product.catalog.domain.entity;

public class ProductVariantValue {
    private Long id;
    private String value;

    public ProductVariantValue() {
    }

    public ProductVariantValue(Long id, String value) {
        this.id = id;
        this.value = value;
    }

    public ProductVariantValue(String value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void update(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ProductVariantValue that = (ProductVariantValue) o;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ProductVariantValue{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}
