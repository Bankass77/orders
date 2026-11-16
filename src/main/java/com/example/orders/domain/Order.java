package com.example.orders.domain;

import com.example.orders.domain.enums.OrderStatus;
import com.example.orders.excecption.InvalidOrderStateException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Order {
    private final UUID id;

    private final List<OrderLine> orderLines;
    private OrderStatus status;

    private Order(UUID id, List<OrderLine> orderLines, OrderStatus status) {
        this.id = Objects.requireNonNull(id);
        if (orderLines == null || orderLines.isEmpty()) {
            throw new IllegalArgumentException("orderLines must be contains at least one order line");
        }
        this.orderLines = List.copyOf(orderLines);
        this.status = Objects.requireNonNull(status);
    }

    public static Order create(List<OrderLine> orderLines) {

        return new Order(UUID.randomUUID(), orderLines, OrderStatus.NEW);
    }

    public static Order reconstitute(UUID id, List<OrderLine> orderLines, OrderStatus status) {
        return new Order(id, orderLines, status);
    }

    public void pay() {

        if (status != OrderStatus.NEW) {
            throw new InvalidOrderStateException("Only NEW Order can be PAID");
        }
        status = OrderStatus.PAID;
    }

    public void cancel() {

        if (status != OrderStatus.NEW) {
            throw new InvalidOrderStateException("Only NEW Order can be CANCELLED");
        }
        status = OrderStatus.CANCELLED;
    }

    public UUID id() {
        return id;
    }

    public List<OrderLine> lines() {
        return orderLines;
    }

    public OrderStatus status() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Order && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
