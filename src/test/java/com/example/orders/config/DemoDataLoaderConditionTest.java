package com.example.orders.config;

import com.example.orders.application.service.OrderApplicationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.mockito.Mockito.mock;

public class DemoDataLoaderConditionTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withBean(OrderApplicationService.class, () -> mock(OrderApplicationService.class))
            .withUserConfiguration(DemoDataLoader.class);


    @Test
    void absent_by_default() {
        contextRunner.run((context) -> {
            Assertions.assertThat(context).doesNotHaveBean(DemoDataLoader.class);
        });


    }

    @Test
    void present_when_enabled() {
        contextRunner.run((context) -> {
            Assertions.assertThat(context).hasSingleBean(DemoDataLoader.class);
        });
    }
}
