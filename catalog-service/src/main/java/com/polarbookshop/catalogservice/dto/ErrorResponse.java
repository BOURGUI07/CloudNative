package com.polarbookshop.catalogservice.dto;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
        String apiPath,
        HttpStatus status,
        int statusCode,
        String message,
        long timeStamp
) {
}
