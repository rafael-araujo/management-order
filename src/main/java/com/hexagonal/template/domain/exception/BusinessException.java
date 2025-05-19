package com.hexagonal.template.domain.exception;

import com.hexagonal.template.domain.model.error.ErrorResponse;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessException extends RuntimeException {

    private ErrorResponse errorResponse;

    public BusinessException(String title, String description, Integer status) {
        this.errorResponse = new ErrorResponse(title, description, status);
    }
}