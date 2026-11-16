package com.example.orders.domain;

import com.example.orders.domain.enums.OrderStatus;
import com.example.orders.excecption.InvalidOrderStateException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

class OrderTest {

    private static List<OrderLine> ONE_LINE = List.of(new OrderLine("A1", 2));

    @Test
    void new_order_is_NEW_with_an_id() {

        var order = Order.create(ONE_LINE);
        Assertions.assertThat(order.id()).isNotNull();
        Assertions.assertThat(order.status()).isEqualTo(OrderStatus.NEW);
        Assertions.assertThat(order.lines()).containsExactly(new OrderLine("A1", 2));
    }

    @Test
    void two_orders_have_distinct_ids() {
        Assertions.assertThat(Order.create(ONE_LINE).id()).isNotEqualTo(Order.create(ONE_LINE).id());
    }

    @Test
    void rejects_empty_or_null_lines() {
        Assertions.assertThatThrownBy(() -> Order.create(List.of())).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> Order.create(null)).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void exposed_lines_are_immutable() {
        var order = Order.create(ONE_LINE);
        Assertions.assertThatThrownBy(() -> order.lines().add(new OrderLine("B2", 1)))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void later_changes_to_the_source_list_do_not_leak_in() {
        var source = new ArrayList<>(ONE_LINE);
        var order = Order.create(source);
        source.add(new OrderLine("B2", 1));
        Assertions.assertThat(order.lines()).hasSize(1);
    }

    @Test
    void a_new_order_can_be_paid() {
        var order = Order.create(ONE_LINE);
        order.pay();
        Assertions.assertThat(order.status()).isEqualTo(OrderStatus.PAID);
    }

    @Test
    void cannot_pay_twice() {
        var order = Order.create(ONE_LINE);
        order.pay();
        Assertions.assertThatThrownBy(order::pay).isInstanceOf(InvalidOrderStateException.class);
    }

    @Test
    void a_order_can_be_cancelled() {
        var order = Order.create(ONE_LINE);
        order.cancel();
        Assertions.assertThat(order.status()).isEqualTo(OrderStatus.CANCELLED);
    }

    @Test
    void cannot_cancel_a_paid_order() {
        var order = Order.create(ONE_LINE);
        order.pay();
        Assertions.assertThatThrownBy(order::cancel).isInstanceOf(InvalidOrderStateException.class);
    }

    @Test
    void reconstitute_keeps_id_and_status() {
        var id = UUID.randomUUID();
        var order = Order.reconstitute(id, ONE_LINE, OrderStatus.PAID);
        Assertions.assertThat(order.id()).isEqualTo(id);
        Assertions.assertThat(order.status()).isEqualTo(OrderStatus.PAID);
    }

    @Test
    void equality_is_based_on_the_immutable_id() {

        var id = UUID.randomUUID();
        var order = Order.reconstitute(id, ONE_LINE, OrderStatus.NEW);
        var order2 = Order.reconstitute(id, ONE_LINE, OrderStatus.PAID);
        Assertions.assertThat(order).isEqualTo(order2).hasSameHashCodeAs(order2);
        Assertions.assertThat(order).isNotEqualTo(Order.create(ONE_LINE));
    }

    @Test
    void hash_based_collections_need_an_immutable_identy() {

        var order = Order.create(ONE_LINE);

        var good = new HashSet<Order>();
        good.add(order);

        var bad = new HashSet<StatusSensitiveOrder>();
        var badKey = new StatusSensitiveOrder(order);
        bad.add(badKey);

        order.pay();  // le statut change, l'id non

        Assertions.assertThat(good).contains(order);

        Assertions.assertThat(bad.contains(badKey)).isFalse();
    }

    private static class StatusSensitiveOrder {

        private final Order order;

        public StatusSensitiveOrder(Order order) {
            this.order = order;

        }

        @Override
        public boolean equals(Object o) {

            return o instanceof StatusSensitiveOrder s &&
                    s.order.id().equals(order.id())
                    && s.order.status().equals(order.status());
        }

        @Override
        public int hashCode() {
            return Objects.hash(order.id(), order.status());
        }
    }
}