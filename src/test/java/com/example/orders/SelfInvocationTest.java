package com.example.orders;


import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.SimpleTransactionStatus;

import java.util.concurrent.atomic.AtomicInteger;

public class SelfInvocationTest {

    static final AtomicInteger OPENED = new AtomicInteger();

    static class CountingTxManager implements PlatformTransactionManager {

        @Override
        public @NotNull TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
            OPENED.incrementAndGet();
            return new SimpleTransactionStatus();
        }

        @Override
        public void commit(@NotNull TransactionStatus status) throws TransactionException {

        }

        @Override
        public void rollback(@NotNull TransactionStatus status) throws TransactionException {

        }
    }

    static class OrderProcessing {

        public void outer() {

            inner();  // appel interne : pas de proxy
        }

        @Transactional
        public void inner() {
        }
    }

    @Configuration
    @EnableTransactionManagement
    static class Config {
        @Bean
        PlatformTransactionManager tm() {
            return new CountingTxManager();
        }

        @Bean
        OrderProcessing self() {
            return new OrderProcessing();
        }
    }

    @Test
    void internal_call_bypasses_the_proxy() {

        new ApplicationContextRunner().withUserConfiguration(Config.class).run(context -> {

            var processing = context.getBean(OrderProcessing.class);
            OPENED.set(0);
            processing.outer();
            Assertions.assertThat(OPENED.get()).isZero(); // Aucune Transaction

            processing.inner();
            Assertions.assertThat(OPENED.get()).isEqualTo(1); // appel externe: proxy actif
        });
    }
}
