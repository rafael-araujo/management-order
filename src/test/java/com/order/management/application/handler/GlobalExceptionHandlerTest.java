package com.order.management.application.handler;

import com.order.management.domain.exception.BusinessException;
import com.order.management.domain.model.error.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler handler;

    @Mock
    private BusinessException businessException;

    @Mock
    private MethodArgumentNotValidException validationException;

    @Mock
    private BindingResult bindingResult;

    @Test
    void handleBusinessException_returnsResponseEntityWithErrorResponseAndStatus() {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(409)
                .title("Conflict Error")
                .description("Resource already exists")
                .build();
        when(businessException.getErrorResponse()).thenReturn(errorResponse);

        ResponseEntity<ErrorResponse> response = handler.handleBusinessException(businessException);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(errorResponse, response.getBody());
    }

    @Test
    void handleBusinessException_returnsInternalServerErrorIfStatusInvalid() {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(999)
                .title("Invalid Status Error")
                .description("Invalid status provided")
                .build();
        when(businessException.getErrorResponse()).thenReturn(errorResponse);

        ResponseEntity<ErrorResponse> response = handler.handleBusinessException(businessException);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(errorResponse, response.getBody());
    }

    @Test
    void handleValidationExceptions_singleNotNullError_returnsBadRequestWithErrorResponse() {

        FieldError notNullError = new FieldError("orderRequest", "customerId", "must not be null");
        when(validationException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(notNullError));

        ResponseEntity<ErrorResponse> response = handler.handleValidationExceptions(validationException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro na validação de campos", response.getBody().getTitle());
        assertEquals("must not be null", response.getBody().getDescription());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    }

    @Test
    void handleValidationExceptions_singleNotEmptyError_returnsBadRequestWithErrorResponse() {

        FieldError notEmptyError = new FieldError("orderRequest", "products", "must not be empty");
        when(validationException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(notEmptyError));

        ResponseEntity<ErrorResponse> response = handler.handleValidationExceptions(validationException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro na validação de campos", response.getBody().getTitle());
        assertEquals("must not be empty", response.getBody().getDescription());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    }

    @Test
    void handleValidationExceptions_singleNotBlankError_returnsBadRequestWithErrorResponse() {

        FieldError notBlankError = new FieldError("orderRequest", "notes", "must not be blank");
        when(validationException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(notBlankError));

        ResponseEntity<ErrorResponse> response = handler.handleValidationExceptions(validationException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro na validação de campos", response.getBody().getTitle());
        assertEquals("must not be blank", response.getBody().getDescription());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    }

    @Test
    void handleValidationExceptions_multipleErrors_returnsBadRequestWithErrorResponseBasedOnPrecedence() {

        FieldError notNullError = new FieldError("orderRequest", "customerId", "must not be null");
        FieldError notEmptyError = new FieldError("orderRequest", "products", "must not be empty");
        FieldError notBlankError = new FieldError("orderRequest", "notes", "must not be blank");
        when(validationException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(notNullError, notEmptyError, notBlankError));

        ResponseEntity<ErrorResponse> response = handler.handleValidationExceptions(validationException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro na validação de campos", response.getBody().getTitle());
        assertEquals("must not be null", response.getBody().getDescription());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    }

    @Test
    void mapError_mapsFieldErrorToErrorResponse() {

        Map<String, ErrorResponse> errors = new HashMap<>();
        FieldError fieldError = new FieldError("orderRequest", "quantity", "must be greater than 0");

        handler.mapError(fieldError, errors);

        assertEquals(1, errors.size());
        assertEquals("Erro na validação de campos", errors.get("Min").getTitle());
        assertEquals("must be greater than 0", errors.get("Min").getDescription());
        assertEquals(HttpStatus.BAD_REQUEST.value(), errors.get("Min").getStatus());
    }

    @Test
    void returnError_containsKeyNotNullOnly_returnsNotNullError() {

        Map<String, ErrorResponse> errors = new HashMap<>();
        ErrorResponse notNullErrorResponse = ErrorResponse.builder().description("NotNull error").build();
        errors.put("NotNull", notNullErrorResponse);

        ErrorResponse result = handler.returnError(errors);

        assertEquals(notNullErrorResponse, result);
    }

    @Test
    void returnError_containsKeyNotNullNotEmptyNotBlank_returnsNotNullError() {

        Map<String, ErrorResponse> errors = new HashMap<>();
        ErrorResponse notNullErrorResponse = ErrorResponse.builder().description("NotNull error").build();
        ErrorResponse notEmptyErrorResponse = ErrorResponse.builder().description("NotEmpty error").build();
        ErrorResponse notBlankErrorResponse = ErrorResponse.builder().description("NotBlank error").build();
        errors.put("NotNull", notNullErrorResponse);
        errors.put("NotEmpty", notEmptyErrorResponse);
        errors.put("NotBlank", notBlankErrorResponse);

        ErrorResponse result = handler.returnError(errors);

        assertEquals(notNullErrorResponse, result);
    }

    @Test
    void returnError_containsKeyNotEmptyNotBlankOnly_returnsNotEmptyError() {

        Map<String, ErrorResponse> errors = new HashMap<>();
        ErrorResponse notEmptyErrorResponse = ErrorResponse.builder().description("NotEmpty error").build();
        ErrorResponse notBlankErrorResponse = ErrorResponse.builder().description("NotBlank error").build();
        errors.put("NotEmpty", notEmptyErrorResponse);
        errors.put("NotBlank", notBlankErrorResponse);

        ErrorResponse result = handler.returnError(errors);

        assertEquals(notEmptyErrorResponse, result);
    }

    @Test
    void returnError_containsKeyNotBlankOnly_returnsNotBlankError() {

        Map<String, ErrorResponse> errors = new HashMap<>();
        ErrorResponse notBlankErrorResponse = ErrorResponse.builder().description("NotBlank error").build();
        errors.put("NotBlank", notBlankErrorResponse);

        ErrorResponse result = handler.returnError(errors);

        assertEquals(notBlankErrorResponse, result);
    }

    @Test
    void returnError_containsNoPriorityKeys_returnsDefaultErrorResponse() {

        Map<String, ErrorResponse> errors = new HashMap<>();
        ErrorResponse otherErrorResponse = ErrorResponse.builder().description("Some other error").build();
        errors.put("Min", otherErrorResponse);

        ErrorResponse result = handler.returnError(errors);

        assertEquals(new ErrorResponse(), result);
    }
}