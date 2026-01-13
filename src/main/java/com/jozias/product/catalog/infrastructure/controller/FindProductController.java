package com.jozias.product.catalog.infrastructure.controller;

import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jozias.product.catalog.application.usecase.FindProductUseCase;
import com.jozias.product.catalog.infrastructure.controller.apiversion.ApiVersion;
import com.jozias.product.catalog.infrastructure.controller.dto.PageResponse;
import com.jozias.product.catalog.infrastructure.controller.dto.ProductDTO;
import com.jozias.product.catalog.infrastructure.controller.dto.ProductDetailDTO;
import com.jozias.product.catalog.infrastructure.controller.mapper.ProductResponseMapper;

@Tag(name = "Produtos")
@Slf4j
@RestController
@RequestMapping(ApiVersion.V1 + "/products")
public class FindProductController {

    private final FindProductUseCase findProductUseCase;
    private final ProductResponseMapper productResponseMapper;

    public FindProductController(FindProductUseCase findProductUseCase, ProductResponseMapper productResponseMapper) {
        this.findProductUseCase = findProductUseCase;
        this.productResponseMapper = productResponseMapper;
    }

    @Operation(summary = "Lista todos os produtos", description = "Retorna uma lista paginada de todos os produtos disponíveis.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public PageResponse<ProductDTO> listAll(@ParameterObject Pageable pageable) {
        Page<ProductDTO> page = findProductUseCase.findAll(pageable).map(productResponseMapper::toDto);
        return PageResponse.from(page);
    }

    @Operation(summary = "Busca detalhes de um produto", description = "Retorna os detalhes completos de um produto pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Produto encontrado")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    @GetMapping("/{id}")
    public ProductDetailDTO detailById(@PathVariable Long id) {
        log.info("Fetching details for product ID: {}", id);
        return productResponseMapper.toDetailDto(findProductUseCase.findById(id));
    }

}