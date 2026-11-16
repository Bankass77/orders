package com.example.orders.config;

import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "orders.api")
@Validated
public record OrderApiProperties(
        @Min(1) int defaultPageSize,
        @Min(1) int maxPageSize) {

    public OrderApiProperties{
        if (defaultPageSize > maxPageSize) {
            throw new IllegalArgumentException( "defautPageSize ne peut pas dépasser maxPageSize." );
        }
    }
}
