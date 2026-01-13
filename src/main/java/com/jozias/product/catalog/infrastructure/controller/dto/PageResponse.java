package com.jozias.product.catalog.infrastructure.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Generic paginated response wrapper with clean, relevant pagination metadata.
 *
 * @param <T> the type of content in the page
 */
@Schema(description = "Resposta paginada com metadados de navegação")
public record PageResponse<T>(
        @Schema(description = "Conteúdo da página") List<T> content,

        @Schema(description = "Número da página atual (começa em 0)", example = "0") int page,

        @Schema(description = "Quantidade de itens por página", example = "20") int size,

        @Schema(description = "Total de elementos disponíveis", example = "100") long totalElements,

        @Schema(description = "Total de páginas disponíveis", example = "5") int totalPages,

        @Schema(description = "Indica se é a primeira página", example = "true") boolean first,

        @Schema(description = "Indica se é a última página", example = "false") boolean last) {
    /**
     * Factory method to create a PageResponse from a Spring Data Page.
     *
     * @param page the Spring Data Page
     * @param <T>  the type of content
     * @return a PageResponse with clean pagination metadata
     */
    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast());
    }
}
