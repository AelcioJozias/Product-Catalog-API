package com.jozias.product.catalog.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jozias.product.catalog.application.dto.UpdateProductDTO;
import com.jozias.product.catalog.application.usecase.UpdateProductUseCase;
import com.jozias.product.catalog.domain.entity.Condition;
import com.jozias.product.catalog.domain.entity.Product;
import com.jozias.product.catalog.domain.entity.ProductVariant;
import com.jozias.product.catalog.infrastructure.controller.apiversion.ApiVersion;
import com.jozias.product.catalog.infrastructure.controller.dto.ProductResponse;
import com.jozias.product.catalog.infrastructure.controller.dto.UpdateProductRequest;
import com.jozias.product.catalog.infrastructure.controller.mapper.ProductRequestMapper;
import com.jozias.product.catalog.infrastructure.controller.mapper.ProductResponseMapper;

import java.util.List;

@Tag(name = "Produtos")
@Slf4j
@RestController
@RequestMapping(ApiVersion.V1 + "/products")
public class UpdateProductController {

    private final UpdateProductUseCase updateProductUseCase;
    private final ProductRequestMapper productRequestMapper;
    private final ProductResponseMapper productResponseMapper;

    public UpdateProductController(
            UpdateProductUseCase updateProductUseCase,
            ProductRequestMapper productRequestMapper,
            ProductResponseMapper productResponseMapper) {
        this.updateProductUseCase = updateProductUseCase;
        this.productRequestMapper = productRequestMapper;
        this.productResponseMapper = productResponseMapper;
    }

    @Operation(summary = "Atualiza um produto existente", description = "Atualiza os dados de um produto a partir do seu ID.")
    @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequest request) {
        log.info("Updating product ID: {}", id);
        List<ProductVariant> variants = productRequestMapper.toUpdateDomainVariants(request.variants());

        UpdateProductDTO dto = new UpdateProductDTO(
                id,
                request.name(),
                request.description(),
                request.price(),
                request.availableQuantity(),
                Condition.valueOf(request.condition()),
                request.category(),
                variants);

        Product updatedProduct = updateProductUseCase.execute(dto);

        return ResponseEntity.ok(productResponseMapper.toResponse(updatedProduct));
    }
}