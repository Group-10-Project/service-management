package com.review.servicemanagement.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ExceptionDTO {

    private HttpStatus code;
    private String message;

    public ExceptionDTO(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }
}