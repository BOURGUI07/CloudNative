package com.polarbookshop.order_service.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties(prefix = "polar")
public record ClientProperties(
        URI catalogServiceUri
) {
}
