package com.jozias.product.catalog.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jozias.product.catalog.application.usecase.DeleteProductUsecase;
import com.jozias.product.catalog.infrastructure.controller.apiversion.ApiVersion;

@Tag(name = "Produtos")
@Slf4j
@RestController
@RequestMapping(ApiVersion.V1 + "/products")
public class DeleteProductController {

    private final DeleteProductUsecase deleteProductUsecase;

    public DeleteProductController(DeleteProductUsecase deleteProductUsecase) {
        this.deleteProductUsecase = deleteProductUsecase;
    }

    @Operation(summary = "Remove um produto", description = "Remove um produto do inventário pelo seu ID.")
    @ApiResponse(responseCode = "204", description = "Produto removido com sucesso")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("Request to delete product ID: {}", id);
        deleteProductUsecase.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
