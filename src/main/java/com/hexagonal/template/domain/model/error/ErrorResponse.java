package com.hexagonal.template.domain.model.error;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private String title;
    private String description;
    private Integer status;
    private String timestamp;

    public ErrorResponse(String title, String description, Integer status) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.timestamp = Instant.now().toString();
    }

}