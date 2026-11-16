package com.example.orders;

import com.example.orders.adapter.FakeCardGateway;
import com.example.orders.adapter.FakeTransfertGateway;
import com.example.orders.application.port.PaymentGatewayPort;
import com.example.orders.application.service.OrderApplicationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.stereotype.Component;

class PaymentWiringText {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner().withUserConfiguration(FakeCardGateway.class, FakeTransfertGateway.class);


    @Test
    void qualifier_selects_the_card_gateway() {

        contextRunner.withUserConfiguration(OrderApplicationService.class).run(
                context-> {
            Assertions.assertThat(context).hasNotFailed();
            Assertions.assertThat(context).hasSingleBean(OrderApplicationService.class);
            Assertions.assertThat(context.getBeansOfType(PaymentGatewayPort.class)).hasSize(2);
        });
    }

    @Test
    void unqualified_injection_is_ambiguous_and_fails_at_startup() {
        contextRunner.withUserConfiguration(Unqualified.class).run((context) -> {

            Assertions.assertThat(context).hasFailed().getFailure().hasRootCauseInstanceOf(NoUniqueBeanDefinitionException.class);

        });
    }

    @Component
    static class Unqualified {
        Unqualified(PaymentGatewayPort paymentGatewayPort) {

        }
    }

}
