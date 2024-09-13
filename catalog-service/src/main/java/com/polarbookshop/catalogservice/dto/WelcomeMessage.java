package com.polarbookshop.catalogservice.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "welcome")
public record WelcomeMessage(
        String message,
        String team
) {
}
