package com.jozias.product.catalog.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jozias.product.catalog.application.dto.CreateProductDTO;
import com.jozias.product.catalog.application.usecase.CreateProductUseCase;
import com.jozias.product.catalog.domain.entity.Condition;
import com.jozias.product.catalog.domain.entity.Product;
import com.jozias.product.catalog.domain.entity.ProductVariant;
import com.jozias.product.catalog.infrastructure.controller.apiversion.ApiVersion;
import com.jozias.product.catalog.infrastructure.controller.dto.CreateProductRequest;
import com.jozias.product.catalog.infrastructure.controller.dto.ProductResponse;
import com.jozias.product.catalog.infrastructure.controller.mapper.ProductRequestMapper;
import com.jozias.product.catalog.infrastructure.controller.mapper.ProductResponseMapper;

import java.util.List;

@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
@Slf4j
@RestController
@RequestMapping(ApiVersion.V1 + "/products")
public class SaveProductController {

    private final CreateProductUseCase createProductUseCase;
    private final ProductRequestMapper productRequestMapper;
    private final ProductResponseMapper productResponseMapper;

    public SaveProductController(
            CreateProductUseCase createProductUseCase,
            ProductRequestMapper productRequestMapper,
            ProductResponseMapper productResponseMapper) {
        this.createProductUseCase = createProductUseCase;
        this.productRequestMapper = productRequestMapper;
        this.productResponseMapper = productResponseMapper;
    }

    @Operation(summary = "Cria um novo produto", description = "Cria um novo produto no inventário para um vendedor específico.")
    @ApiResponse(responseCode = "201", description = "Produto criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos")
    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody CreateProductRequest request) {
        log.info("Creating product: {} for seller: {}", request.name(), request.sellerId());
        List<ProductVariant> variants = productRequestMapper.toCreateDomainVariants(request.variants());

        CreateProductDTO dto = new CreateProductDTO(
                request.sellerId(),
                request.name(),
                request.description(),
                request.price(),
                request.availableQuantity(),
                Condition.valueOf(request.condition()),
                request.category(),
                variants);

        Product createdProduct = createProductUseCase.execute(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(productResponseMapper.toResponse(createdProduct));
    }
}