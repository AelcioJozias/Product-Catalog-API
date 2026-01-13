package com.jozias.product.catalog.infrastructure.controller.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.jozias.product.catalog.domain.exception.EntityNotFoundException;
import com.jozias.product.catalog.domain.exception.ProductInstanceInvalidException;
import com.jozias.product.catalog.infrastructure.controller.dto.ApiErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

        private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        private static final String VALIDATION_ERROR_TYPE = "https://api.example.com/errors/validation";
        private static final String NOT_FOUND_TYPE = "https://api.example.com/errors/not-found";
        private static final String BUSINESS_ERROR_TYPE = "https://api.example.com/errors/business";
        private static final String INTERNAL_ERROR_TYPE = "https://api.example.com/errors/internal";
        private static final String UNKNOWN_TYPE = "unknown";

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<ApiErrorResponse> handleEntityNotFound(
                        EntityNotFoundException ex,
                        HttpServletRequest request) {
                logger.warn("Entity not found: {}", ex.getMessage());

                ApiErrorResponse response = ApiErrorResponse.of(
                                NOT_FOUND_TYPE,
                                "Resource Not Found",
                                HttpStatus.NOT_FOUND.value(),
                                ex.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        @ExceptionHandler(ProductInstanceInvalidException.class)
        public ResponseEntity<ApiErrorResponse> handleProductInstanceInvalid(
                        ProductInstanceInvalidException ex,
                        HttpServletRequest request) {
                logger.warn("Business validation failed: {}", ex.getMessage());

                ApiErrorResponse response = ApiErrorResponse.of(
                                BUSINESS_ERROR_TYPE,
                                "Business Validation Error",
                                HttpStatus.BAD_REQUEST.value(),
                                ex.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiErrorResponse> handleValidationErrors(
                        MethodArgumentNotValidException ex,
                        HttpServletRequest request) {
                logger.warn("Validation failed for request to {}", request.getRequestURI());

                List<ApiErrorResponse.FieldError> fieldErrors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> new ApiErrorResponse.FieldError(
                                                error.getField(),
                                                error.getDefaultMessage()))
                                .toList();

                ApiErrorResponse response = ApiErrorResponse.withErrors(
                                VALIDATION_ERROR_TYPE,
                                "Validation Failed",
                                HttpStatus.BAD_REQUEST.value(),
                                "One or more fields have validation errors",
                                request.getRequestURI(),
                                fieldErrors);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<ApiErrorResponse> handleConstraintViolation(
                        ConstraintViolationException ex,
                        HttpServletRequest request) {
                logger.warn("Constraint violation: {}", ex.getMessage());

                List<ApiErrorResponse.FieldError> fieldErrors = ex.getConstraintViolations()
                                .stream()
                                .map(violation -> new ApiErrorResponse.FieldError(
                                                violation.getPropertyPath().toString(),
                                                violation.getMessage()))
                                .toList();

                ApiErrorResponse response = ApiErrorResponse.withErrors(
                                VALIDATION_ERROR_TYPE,
                                "Validation Failed",
                                HttpStatus.BAD_REQUEST.value(),
                                "One or more constraints were violated",
                                request.getRequestURI(),
                                fieldErrors);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ApiErrorResponse> handleIllegalArgument(
                        IllegalArgumentException ex,
                        HttpServletRequest request) {
                logger.warn("Invalid argument: {}", ex.getMessage());

                ApiErrorResponse response = ApiErrorResponse.of(
                                VALIDATION_ERROR_TYPE,
                                "Invalid Argument",
                                HttpStatus.BAD_REQUEST.value(),
                                ex.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadable(
                        HttpMessageNotReadableException ex,
                        HttpServletRequest request) {

                Throwable cause = ex.getCause();

                // Handle invalid format errors (e.g., invalid enum value)
                // Note: InvalidFormatException extends MismatchedInputException, so check this
                // first
                if (cause instanceof InvalidFormatException invalidFormatEx) {
                        return handleInvalidFormat(invalidFormatEx, request);
                }

                // Handle type mismatch errors (e.g., number passed for String field)
                if (cause instanceof MismatchedInputException mismatchEx) {
                        return handleMismatchedInput(mismatchEx, request);
                }

                // Default handling for other cases
                logger.warn("Malformed request body: {}", ex.getMessage());

                ApiErrorResponse response = ApiErrorResponse.of(
                                VALIDATION_ERROR_TYPE,
                                "Malformed Request",
                                HttpStatus.BAD_REQUEST.value(),
                                "Request body is malformed or contains invalid data",
                                request.getRequestURI());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        private ResponseEntity<ApiErrorResponse> handleMismatchedInput(
                        MismatchedInputException ex,
                        HttpServletRequest request) {

                String fieldName = extractFieldName(ex);
                String expectedType = getSimpleTypeName(ex.getTargetType());
                String receivedType = inferReceivedType(ex);

                String message = String.format("Field '%s' expected type %s but received %s",
                                fieldName, expectedType, receivedType);

                logger.warn("Type mismatch in request body: {}", message);

                List<ApiErrorResponse.FieldError> fieldErrors = List.of(
                                new ApiErrorResponse.FieldError(fieldName, message));

                ApiErrorResponse response = ApiErrorResponse.withErrors(
                                VALIDATION_ERROR_TYPE,
                                "Type Mismatch",
                                HttpStatus.BAD_REQUEST.value(),
                                "One or more fields have incorrect data types",
                                request.getRequestURI(),
                                fieldErrors);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        private ResponseEntity<ApiErrorResponse> handleInvalidFormat(
                        InvalidFormatException ex,
                        HttpServletRequest request) {

                String fieldName = extractFieldName(ex);
                String expectedType = getSimpleTypeName(ex.getTargetType());
                Object invalidValue = ex.getValue();

                String message = String.format("Field '%s' has invalid value '%s' for type %s",
                                fieldName, invalidValue, expectedType);

                logger.warn("Invalid format in request body: {}", message);

                List<ApiErrorResponse.FieldError> fieldErrors = List.of(
                                new ApiErrorResponse.FieldError(fieldName, message));

                ApiErrorResponse response = ApiErrorResponse.withErrors(
                                VALIDATION_ERROR_TYPE,
                                "Invalid Format",
                                HttpStatus.BAD_REQUEST.value(),
                                "One or more fields have invalid format",
                                request.getRequestURI(),
                                fieldErrors);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        private String extractFieldName(JsonMappingException ex) {
                List<JsonMappingException.Reference> path = ex.getPath();
                if (path == null || path.isEmpty()) {
                        return UNKNOWN_TYPE;
                }

                return path.stream()
                                .map(ref -> {
                                        if (ref.getFieldName() != null) {
                                                return ref.getFieldName();
                                        } else if (ref.getIndex() >= 0) {
                                                return "[" + ref.getIndex() + "]";
                                        }
                                        return "";
                                })
                                .filter(s -> !s.isEmpty())
                                .collect(Collectors.joining("."));
        }

        private String getSimpleTypeName(Class<?> type) {
                if (type == null) {
                        return UNKNOWN_TYPE;
                }
                return type.getSimpleName();
        }

        private String inferReceivedType(MismatchedInputException ex) {
                String message = ex.getMessage();
                if (message == null) {
                        return UNKNOWN_TYPE;
                }

                // Jackson includes info about what was received in the message
                if (message.contains("START_ARRAY")) {
                        return "Array";
                } else if (message.contains("START_OBJECT")) {
                        return "Object";
                } else if (message.contains("VALUE_NUMBER_INT") || message.contains("from Integer value")) {
                        return "Integer";
                } else if (message.contains("VALUE_NUMBER_FLOAT") || message.contains("from Floating-point value")) {
                        return "Float";
                } else if (message.contains("VALUE_STRING") || message.contains("from String value")) {
                        return "String";
                } else if (message.contains("VALUE_TRUE") || message.contains("VALUE_FALSE")
                                || message.contains("from Boolean value")) {
                        return "Boolean";
                } else if (message.contains("VALUE_NULL")) {
                        return "null";
                }

                return "incompatible type";
        }

        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<ApiErrorResponse> handleTypeMismatch(
                        MethodArgumentTypeMismatchException ex,
                        HttpServletRequest request) {
                logger.warn("Type mismatch for parameter '{}': {}", ex.getName(), ex.getMessage());

                String message = String.format("Parameter '%s' should be of type %s",
                                ex.getName(),
                                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : UNKNOWN_TYPE);

                ApiErrorResponse response = ApiErrorResponse.of(
                                VALIDATION_ERROR_TYPE,
                                "Type Mismatch",
                                HttpStatus.BAD_REQUEST.value(),
                                message,
                                request.getRequestURI());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiErrorResponse> handleGenericException(
                        Exception ex,
                        HttpServletRequest request) {
                logger.error("Unexpected error processing request to {}", request.getRequestURI(), ex);

                ApiErrorResponse response = ApiErrorResponse.of(
                                INTERNAL_ERROR_TYPE,
                                "Internal Server Error",
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "An unexpected error occurred. Please try again later.",
                                request.getRequestURI());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
}
