package com.switchfully.order.domain.orders;

import com.switchfully.order.domain.orders.Order.OrderBuilder;
import com.switchfully.order.domain.orders.orderitems.OrderItem;
import com.switchfully.order.infrastructure.builder.Builder;

import java.util.Arrays;
import java.util.UUID;

import static com.switchfully.order.domain.orders.orderitems.OrderItemTestBuilder.anOrderItem;

public class OrderTestBuilder extends Builder<Order> {

    private OrderBuilder orderBuilder;

    private OrderTestBuilder(OrderBuilder orderBuilder) {
        this.orderBuilder = orderBuilder;
    }

    public static OrderTestBuilder anEmptyOrder() {
        return new OrderTestBuilder(OrderBuilder.order());
    }

    public static OrderTestBuilder anOrder() {
        return new OrderTestBuilder(OrderBuilder.order()
        .withCustomerId(UUID.randomUUID())
        .withOrderItems(Arrays.asList(anOrderItem().build(), anOrderItem().build())));
    }

    @Override
    public Order build() {
        return new Order(orderBuilder);
    }

    public OrderTestBuilder withId(UUID id) {
        orderBuilder.withId(id);
        return this;
    }

    public OrderTestBuilder withOrderItems(OrderItem... orderItems) {
        orderBuilder.withOrderItems(Arrays.asList(orderItems));
        return this;
    }

    public OrderTestBuilder withCustomerId(UUID customerId) {
        orderBuilder.withCustomerId(customerId);
        return this;
    }
}