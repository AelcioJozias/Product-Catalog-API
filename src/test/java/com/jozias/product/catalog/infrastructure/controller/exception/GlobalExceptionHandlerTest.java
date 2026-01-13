package com.jozias.product.catalog.infrastructure.controller.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.jozias.product.catalog.domain.exception.EntityNotFoundException;
import com.jozias.product.catalog.domain.exception.ProductInstanceInvalidException;
import com.jozias.product.catalog.infrastructure.controller.dto.ApiErrorResponse;
import com.jozias.product.catalog.infrastructure.controller.exception.GlobalExceptionHandler;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("GlobalExceptionHandler")
class GlobalExceptionHandlerTest {

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        when(request.getRequestURI()).thenReturn("/api/v1/products");
    }

    @Test
    @DisplayName("given EntityNotFoundException when handle then should return 404")
    void givenEntityNotFoundException_whenHandle_thenShouldReturn404() {
        // given
        EntityNotFoundException exception = new EntityNotFoundException("Product with id 1 not found");

        // when
        ResponseEntity<ApiErrorResponse> response = exceptionHandler.handleEntityNotFound(exception, request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(404);
        assertThat(response.getBody().detail()).isEqualTo("Product with id 1 not found");
        assertThat(response.getBody().title()).isEqualTo("Resource Not Found");
    }

    @Test
    @DisplayName("given ProductInstanceInvalidException when handle then should return 400")
    void givenProductInstanceInvalidException_whenHandle_thenShouldReturn400() {
        // given
        ProductInstanceInvalidException exception = new ProductInstanceInvalidException("The field 'name' is invalid");

        // when
        ResponseEntity<ApiErrorResponse> response = exceptionHandler.handleProductInstanceInvalid(exception, request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(400);
        assertThat(response.getBody().detail()).isEqualTo("The field 'name' is invalid");
        assertThat(response.getBody().title()).isEqualTo("Business Validation Error");
    }

    @Test
    @DisplayName("given MethodArgumentNotValidException when handle then should return 400 with field errors")
    void givenMethodArgumentNotValidException_whenHandle_thenShouldReturn400WithFieldErrors() {
        // given
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("productRequest", "name", "Name is required");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        MethodParameter methodParameter = mock(MethodParameter.class);
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(methodParameter, bindingResult);

        // when
        ResponseEntity<ApiErrorResponse> response = exceptionHandler.handleValidationErrors(exception, request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(400);
        assertThat(response.getBody().errors()).hasSize(1);
        assertThat(response.getBody().errors().get(0).field()).isEqualTo("name");
        assertThat(response.getBody().errors().get(0).message()).isEqualTo("Name is required");
    }

    @Test
    @DisplayName("given ConstraintViolationException when handle then should return 400")
    void givenConstraintViolationException_whenHandle_thenShouldReturn400() {
        // given
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        Path path = mock(Path.class);
        when(path.toString()).thenReturn("name");
        when(violation.getPropertyPath()).thenReturn(path);
        when(violation.getMessage()).thenReturn("must not be blank");

        ConstraintViolationException exception = new ConstraintViolationException(Set.of(violation));

        // when
        ResponseEntity<ApiErrorResponse> response = exceptionHandler.handleConstraintViolation(exception, request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(400);
        assertThat(response.getBody().errors()).hasSize(1);
    }

    @Test
    @DisplayName("given IllegalArgumentException when handle then should return 400")
    void givenIllegalArgumentException_whenHandle_thenShouldReturn400() {
        // given
        IllegalArgumentException exception = new IllegalArgumentException("Invalid argument value");

        // when
        ResponseEntity<ApiErrorResponse> response = exceptionHandler.handleIllegalArgument(exception, request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(400);
        assertThat(response.getBody().detail()).isEqualTo("Invalid argument value");
    }

    @Test
    @DisplayName("given HttpMessageNotReadableException when handle then should return 400")
    void givenHttpMessageNotReadableException_whenHandle_thenShouldReturn400() {
        // given
        HttpMessageNotReadableException exception = mock(HttpMessageNotReadableException.class);
        when(exception.getMessage()).thenReturn("Invalid JSON");

        // when
        ResponseEntity<ApiErrorResponse> response = exceptionHandler.handleHttpMessageNotReadable(exception, request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(400);
        assertThat(response.getBody().title()).isEqualTo("Malformed Request");
    }

    @Test
    @DisplayName("given MethodArgumentTypeMismatchException when handle then should return 400")
    void givenMethodArgumentTypeMismatchException_whenHandle_thenShouldReturn400() {
        // given
        MethodArgumentTypeMismatchException exception = mock(MethodArgumentTypeMismatchException.class);
        when(exception.getName()).thenReturn("id");
        when(exception.getRequiredType()).thenReturn((Class) Long.class);

        // when
        ResponseEntity<ApiErrorResponse> response = exceptionHandler.handleTypeMismatch(exception, request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(400);
        assertThat(response.getBody().detail()).contains("id");
        assertThat(response.getBody().detail()).contains("Long");
    }

    @Test
    @DisplayName("given MethodArgumentTypeMismatchException with null required type when handle then should return 400")
    void givenMethodArgumentTypeMismatchExceptionWithNullRequiredType_whenHandle_thenShouldReturn400() {
        // given
        MethodArgumentTypeMismatchException exception = mock(MethodArgumentTypeMismatchException.class);
        when(exception.getName()).thenReturn("id");
        when(exception.getRequiredType()).thenReturn(null);

        // when
        ResponseEntity<ApiErrorResponse> response = exceptionHandler.handleTypeMismatch(exception, request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().detail()).contains("unknown");
    }

    @Test
    @DisplayName("given generic Exception when handle then should return 500")
    void givenGenericException_whenHandle_thenShouldReturn500() {
        // given
        Exception exception = new RuntimeException("Unexpected error");

        // when
        ResponseEntity<ApiErrorResponse> response = exceptionHandler.handleGenericException(exception, request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(500);
        assertThat(response.getBody().title()).isEqualTo("Internal Server Error");
        assertThat(response.getBody().detail()).contains("unexpected error");
    }

    @Test
    @DisplayName("given HttpMessageNotReadableException with MismatchedInputException when handle then should return type mismatch error")
    void givenHttpMessageNotReadableExceptionWithMismatchedInput_whenHandle_thenShouldReturnTypeMismatchError() {
        // given
        com.fasterxml.jackson.databind.exc.MismatchedInputException mismatchedException = mock(
                com.fasterxml.jackson.databind.exc.MismatchedInputException.class);
        when(mismatchedException.getTargetType()).thenReturn((Class) String.class);
        when(mismatchedException.getMessage())
                .thenReturn("Cannot coerce Integer value to String from Integer value (1)");
        when(mismatchedException.getPath()).thenReturn(List.of(
                new com.fasterxml.jackson.databind.JsonMappingException.Reference(null, "name")));

        HttpMessageNotReadableException exception = mock(HttpMessageNotReadableException.class);
        when(exception.getCause()).thenReturn(mismatchedException);

        // when
        ResponseEntity<ApiErrorResponse> response = exceptionHandler.handleHttpMessageNotReadable(exception, request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(400);
        assertThat(response.getBody().title()).isEqualTo("Type Mismatch");
        assertThat(response.getBody().errors()).hasSize(1);
        assertThat(response.getBody().errors().get(0).field()).isEqualTo("name");
        assertThat(response.getBody().errors().get(0).message()).contains("String");
        assertThat(response.getBody().errors().get(0).message()).contains("Integer");
    }

    @Test
    @DisplayName("given HttpMessageNotReadableException with InvalidFormatException when handle then should return invalid format error")
    void givenHttpMessageNotReadableExceptionWithInvalidFormat_whenHandle_thenShouldReturnInvalidFormatError() {
        // given
        com.fasterxml.jackson.databind.exc.InvalidFormatException invalidFormatException = mock(
                com.fasterxml.jackson.databind.exc.InvalidFormatException.class);
        when(invalidFormatException.getTargetType())
                .thenReturn((Class) com.jozias.product.catalog.domain.entity.Condition.class);
        when(invalidFormatException.getValue()).thenReturn("INVALID_CONDITION");
        when(invalidFormatException.getPath()).thenReturn(List.of(
                new com.fasterxml.jackson.databind.JsonMappingException.Reference(null, "condition")));

        HttpMessageNotReadableException exception = mock(HttpMessageNotReadableException.class);
        when(exception.getCause()).thenReturn(invalidFormatException);

        // when
        ResponseEntity<ApiErrorResponse> response = exceptionHandler.handleHttpMessageNotReadable(exception, request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(400);
        assertThat(response.getBody().title()).isEqualTo("Invalid Format");
        assertThat(response.getBody().errors()).hasSize(1);
        assertThat(response.getBody().errors().get(0).field()).isEqualTo("condition");
        assertThat(response.getBody().errors().get(0).message()).contains("INVALID_CONDITION");
    }
}
