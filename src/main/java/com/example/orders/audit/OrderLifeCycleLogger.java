package com.example.orders.audit;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class OrderLifeCycleLogger implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (beanName.equals("orderService")) {
            System.out.println("OrderService avant initialisation");
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {

        if (beanName.equals("orderService")) {
            System.out.println("OrderService après initialisation");
        }
        return bean;
    }
}
