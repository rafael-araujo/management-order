package com.hexagonal.template.domain.model.error;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
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
        this.timestamp = LocalDateTime.now().toString();
    }

}