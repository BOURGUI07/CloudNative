package com.polarbookshop.catalogservice.dto;

import org.springframework.http.HttpStatus;

import java.util.Map;

public record ValidationErrorResponse(
        String apiPath,
        String httpMethod,
        HttpStatus status,
        int statusCode,
        Map<String,String> message,
        long timeStamp
) {
}
