package com.polarbookshop.catalogservice.dto;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
        String apiPath,
        String httpMethod,
        HttpStatus status,
        int statusCode,
        String message,
        long timeStamp
) {
}
