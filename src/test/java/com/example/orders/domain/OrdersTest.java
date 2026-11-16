package com.example.orders.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrdersTest {


    @Test
    void ordersTest_should_throw_UnsupportedOperationException() {

        List<OrderLine> orderLines = new ArrayList<>();
        OrderLine orderLine = new OrderLine("Samsung", 100);
        orderLines.add(orderLine);
        Order order = Order.create(orderLines);

        assertThrows(UnsupportedOperationException.class, () -> {

            order.lines().add(new OrderLine("Apple", 200));
        });
    }

    @Test
    void ordersTest_should_throw_IllegalArgumentException_when_quantity_is_less_than_1() {

        assertThrows(IllegalArgumentException.class, () -> {
            new OrderLine("Samsung", 0);
        });
    }

    @Test
    void ordersTest_should_throw_IllegalArgumentException_when_quantity_is_less_than_0() {

        assertThrows(IllegalArgumentException.class, () -> {
            new OrderLine("Samsung", -1);
        });
    }

    @Test
    void ordersTest_should_Not_throw_IllegalArgumentException_when_quantity_is_greeter_than_0() {
        OrderLine orderLine = new OrderLine("Samsung", 1);

        assertEquals(1, orderLine.quantity());
        assertDoesNotThrow(orderLine::quantity);

    }

    @Test
    void should_reject_blank_sku_when_quantity_is_not_zero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OrderLine(" ", 3);
        });
    }

}