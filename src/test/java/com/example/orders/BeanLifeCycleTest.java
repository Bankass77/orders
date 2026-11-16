package com.example.orders;

import jakarta.annotation.PostConstruct;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import java.util.ArrayList;
import java.util.List;

public class BeanLifeCycleTest {
    static final List<String> EVENTS = new ArrayList<>();

    static class Tracked implements InitializingBean {

        Tracked() {
            EVENTS.add("constructor");
        }

        @PostConstruct
        void postConstruct() {
            EVENTS.add("postConstruct");
        }

        @Override
        public void afterPropertiesSet() throws Exception {

            EVENTS.add("afterPropertiesSet");
        }

        static class Recorder implements BeanPostProcessor {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String name) {
                if (bean instanceof Tracked) {
                    EVENTS.add("bpp-before");
                }
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String name) {

                if (bean instanceof Tracked) {
                    EVENTS.add("bpp-after");
                }
                return bean;
            }
        }

        @Test
        void lifecycle_steps_happen_in_a_known_order() {

            EVENTS.clear();
            new ApplicationContextRunner().withBean(Tracked.class).withBean(Recorder.class).run((context) -> {

                System.out.println(EVENTS);
                Assertions.assertThat(EVENTS.get(0)).isEqualTo("constructor");
                Assertions.assertThat(EVENTS.get(EVENTS.size()-1)).isEqualTo("bpp-after");
                Assertions.assertThat(EVENTS.indexOf("postConstruct")).isLessThan(EVENTS.indexOf("bpp-after"));
                Assertions.assertThat(EVENTS.indexOf("bpp-before")).isLessThan(EVENTS.indexOf("afterPropertiesSet"));
            });
        }

    }
}
