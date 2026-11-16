package com.example.orders.domain;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OrderLineTest {

    @Test
    void create_a_valid() {
        var line = new OrderLine("A1", 2);
        Assertions.assertThat(line.sku()).isEqualTo("A1");
        Assertions.assertThat(line.quantity()).isEqualTo(2);
    }

    @ParameterizedTest
    @ValueSource(
            ints = {0, -1, -100})
    void project_non_positive_quantity(int quantity) {

        Assertions.assertThatThrownBy(()-> new OrderLine("A1", quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("quantity must be greater than 0");
    }

    @ParameterizedTest
    @ValueSource(strings = {

            "", " ","  "
    })
    void reject_blak_sku(String  sku){

        Assertions.assertThatThrownBy( ()-> new OrderLine(sku, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("sku");
    }

    @Test
    void reject_null_sku(){

        Assertions.assertThatThrownBy( () -> new OrderLine(null, 1))
                .isInstanceOf(IllegalArgumentException.class);
    }

}