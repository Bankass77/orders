package com.example.orders.config;

import com.example.orders.audit.OrderAuditLogger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        name = "orders.audit.enabled",
        havingValue = "true"
)
public class OrderAuditConfiguration {

    @Bean
    OrderAuditLogger orderAuditLogger() {
        return new OrderAuditLogger();
    }
}
