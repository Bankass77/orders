package com.example.orders.config;

import com.example.orders.application.service.OrderApplicationService;
import com.example.orders.domain.OrderLine;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(name = "orders.demo-data.enabled", havingValue = "true")
public class DemoDataLoader implements CommandLineRunner {

    private final OrderApplicationService orderApplicationService;

    public DemoDataLoader(OrderApplicationService orderApplicationService) {
        this.orderApplicationService = orderApplicationService;
    }

    @Override
    public void run(String... args) throws Exception {

        orderApplicationService.createOrder(List.of( new OrderLine("DEMO-1", 1)));
        orderApplicationService.createOrder(List.of( new OrderLine("DEMO-2", 2), new OrderLine("DEMO-3", 5)));
        orderApplicationService.createOrder(List.of( new OrderLine("DEMO-4", 10)));
    }
}
