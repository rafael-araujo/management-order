package com.order.management.application.handler;

import com.order.management.domain.exception.BusinessException;
import com.order.management.domain.model.error.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleBusinessException() {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .title("Business Error")
                .description("Business rule violated")
                .status(400)
                .timestamp(LocalDateTime.now().toString())
                .build();

        BusinessException businessException = new BusinessException(errorResponse);

        ResponseEntity<ErrorResponse> response = handler.handleBusinessException(businessException);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Business Error", response.getBody().getTitle());
        assertEquals("Business rule violated", response.getBody().getDescription());
    }

    @Test
    void handleBusinessExceptionWithInvalidStatus() {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .title("Business Error")
                .description("Business rule violated")
                .status(999)
                .timestamp(LocalDateTime.now().toString())
                .build();

        BusinessException businessException = new BusinessException(errorResponse);

        ResponseEntity<ErrorResponse> response = handler.handleBusinessException(businessException);

        assertEquals(500, response.getStatusCode().value());
    }

    @Test
    void handleValidationExceptions() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        List<ObjectError> errors = new ArrayList<>();
        errors.add(new FieldError("object", "field", null, false, new String[]{"NotNull"}, null, "Field must not be null"));

        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(errors);

        ResponseEntity<ErrorResponse> response = handler.handleValidationExceptions(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro na validação de campos", response.getBody().getTitle());
        assertEquals("Field must not be null", response.getBody().getDescription());
    }

    @Test
    void mapError() {
        ObjectError error = new FieldError("object", "field", null, false, new String[]{"NotNull"}, null, "Field must not be null");
        Map<String, ErrorResponse> errors = new HashMap<>();

        handler.mapError(error, errors);

        assertTrue(errors.containsKey("NotNull"));
        assertEquals("Erro na validação de campos", errors.get("NotNull").getTitle());
        assertEquals("Field must not be null", errors.get("NotNull").getDescription());
        assertEquals(HttpStatus.BAD_REQUEST.value(), errors.get("NotNull").getStatus());
        assertNotNull(errors.get("NotNull").getTimestamp());
    }

    @Test
    void returnError_WithNotNull() {
        Map<String, ErrorResponse> errors = new HashMap<>();

        ErrorResponse notNullError = ErrorResponse.builder()
                .title("Erro na validação de campos")
                .description("Field must not be null")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now().toString())
                .build();

        errors.put("NotNull", notNullError);

        ErrorResponse result = handler.returnError(errors);

        assertEquals(notNullError, result);
    }

    @Test
    void returnError_WithNotNullAndNotEmptyAndNotBlank() {
        Map<String, ErrorResponse> errors = new HashMap<>();

        ErrorResponse notNullError = ErrorResponse.builder()
                .title("Erro na validação de campos")
                .description("Field must not be null")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now().toString())
                .build();

        ErrorResponse notEmptyError = ErrorResponse.builder()
                .title("Erro na validação de campos")
                .description("Field must not be empty")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now().toString())
                .build();

        ErrorResponse notBlankError = ErrorResponse.builder()
                .title("Erro na validação de campos")
                .description("Field must not be blank")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now().toString())
                .build();

        errors.put("NotNull", notNullError);
        errors.put("NotEmpty", notEmptyError);
        errors.put("NotBlank", notBlankError);

        ErrorResponse result = handler.returnError(errors);

        assertEquals(notNullError, result);
    }

    @Test
    void returnError_WithNotEmptyAndNotBlank() {
        Map<String, ErrorResponse> errors = new HashMap<>();

        ErrorResponse notEmptyError = ErrorResponse.builder()
                .title("Erro na validação de campos")
                .description("Field must not be empty")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now().toString())
                .build();

        ErrorResponse notBlankError = ErrorResponse.builder()
                .title("Erro na validação de campos")
                .description("Field must not be blank")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now().toString())
                .build();

        errors.put("NotEmpty", notEmptyError);
        errors.put("NotBlank", notBlankError);

        ErrorResponse result = handler.returnError(errors);

        assertEquals(notEmptyError, result);
    }

    @Test
    void returnError_WithNotBlank() {
        Map<String, ErrorResponse> errors = new HashMap<>();

        ErrorResponse notBlankError = ErrorResponse.builder()
                .title("Erro na validação de campos")
                .description("Field must not be blank")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now().toString())
                .build();

        errors.put("NotBlank", notBlankError);

        ErrorResponse result = handler.returnError(errors);

        assertEquals(notBlankError, result);
    }

    @Test
    void returnError_Empty() {
        Map<String, ErrorResponse> errors = new HashMap<>();

        ErrorResponse result = handler.returnError(errors);

        assertNotNull(result);
    }
}