package com.jozias.product.catalog.infrastructure.gateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.jozias.product.catalog.domain.entity.Condition;
import com.jozias.product.catalog.domain.entity.Product;
import com.jozias.product.catalog.domain.entity.Seller;
import com.jozias.product.catalog.infrastructure.gateway.FindProductGatewayImpl;
import com.jozias.product.catalog.infrastructure.persistence.entity.ProductEntity;
import com.jozias.product.catalog.infrastructure.persistence.entity.SellerEntity;
import com.jozias.product.catalog.infrastructure.persistence.mapper.ProductEntityMapper;
import com.jozias.product.catalog.infrastructure.persistence.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("FindProductGatewayImpl")
class FindProductGatewayImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductEntityMapper productEntityMapper;

    @InjectMocks
    private FindProductGatewayImpl findProductGateway;

    private ProductEntity productEntity;
    private Product product;

    @BeforeEach
    void setUp() {
        SellerEntity sellerEntity = new SellerEntity();
        sellerEntity.setId(1L);
        sellerEntity.setName("Tech Store");
        sellerEntity.setDescription("Best tech products");
        sellerEntity.setScore(95);

        productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setName("Smartphone");
        productEntity.setDescription("A great smartphone");
        productEntity.setPrice(new BigDecimal("999.99"));
        productEntity.setAvailableQuantity(10);
        productEntity.setCondition(Condition.NEW);
        productEntity.setCategory("Electronics");
        productEntity.setSeller(sellerEntity);
        productEntity.setVariants(new ArrayList<>());

        Seller seller = new Seller("Tech Store", "Best tech products", 95);
        seller.setId(1L);

        product = new Product(
                1L,
                "Smartphone",
                "A great smartphone with amazing features",
                new BigDecimal("999.99"),
                10,
                Condition.NEW,
                "Electronics",
                new ArrayList<>(),
                seller);
    }

    @Test
    @DisplayName("given products exist when list then should return mapped page")
    void givenProductsExist_whenList_thenShouldReturnMappedPage() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductEntity> entityPage = new PageImpl<>(List.of(productEntity), pageable, 1);

        when(productRepository.findAll(pageable)).thenReturn(entityPage);
        when(productEntityMapper.toDomain(any(ProductEntity.class))).thenReturn(product);

        // when
        Page<Product> result = findProductGateway.list(pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(product);
        verify(productRepository).findAll(pageable);
    }

    @Test
    @DisplayName("given existing id when findById then should return mapped product")
    void givenExistingId_whenFindById_thenShouldReturnMappedProduct() {
        // given
        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));
        when(productEntityMapper.toDomain(productEntity)).thenReturn(product);

        // when
        Optional<Product> result = findProductGateway.findById(1L);

        // then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(product);
        verify(productRepository).findById(1L);
    }

    @Test
    @DisplayName("given non-existent id when findById then should return empty")
    void givenNonExistentId_whenFindById_thenShouldReturnEmpty() {
        // given
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        // when
        Optional<Product> result = findProductGateway.findById(999L);

        // then
        assertThat(result).isEmpty();
        verify(productRepository).findById(999L);
    }
}
