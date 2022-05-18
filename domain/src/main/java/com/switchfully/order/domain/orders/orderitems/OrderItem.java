package com.switchfully.order.domain.orders.orderitems;

import com.switchfully.order.domain.items.prices.Price;
import com.switchfully.order.infrastructure.builder.Builder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

/**
 * OrderItem is a fabricated (value) object consisting of the original Item's id and price, enriched with
 * order-specific information (the ordered amount and the shipping date).
 */
@Embeddable
public final class OrderItem {

    @Column(name = "ORDERED_AMOUNT")
    private final int orderedAmount;

    @Column(name = "SHIPPING_DATE")
    private final LocalDate shippingDate;

    @Column(name = "ITEM_ID")
    private final String itemId;

    @Embedded
    private final Price itemPrice;

    /** JPA requires a no-arg constructor */
    private OrderItem() {
        orderedAmount = 0;
        shippingDate = null;
        itemId = null;
        itemPrice = null;
    }

    public OrderItem(OrderItemBuilder orderItemBuilder, Clock clock) {
        itemId = orderItemBuilder.itemId.toString();
        itemPrice = orderItemBuilder.itemPrice;
        orderedAmount = orderItemBuilder.orderedAmount;
        shippingDate = calculateShippingDate(orderItemBuilder.availableItemStock, clock);
    }

    private LocalDate calculateShippingDate(int availableItemStock, Clock clock) {
        if(availableItemStock - orderedAmount >= 0) {
            return LocalDate.now(clock).plusDays(1);
        } return LocalDate.now(clock).plusDays(7);
    }

    public UUID getItemId() {
        return UUID.fromString(itemId);
    }

    public Price getItemPrice() {
        return itemPrice;
    }

    public int getOrderedAmount() {
        return orderedAmount;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    public Price getTotalPrice() {
        return Price.create(itemPrice.getAmount()
                .multiply(BigDecimal.valueOf(orderedAmount)));
    }

    @Override
    public String toString() {
        return "OrderItem{" + "itemId=" + itemId +
                ", itemPrice=" + itemPrice +
                ", orderedAmount=" + orderedAmount +
                ", shippingDate=" + shippingDate +
                '}';
    }

    public static class OrderItemBuilder extends Builder<OrderItem> {

        private UUID itemId;
        private Price itemPrice;
        private int orderedAmount;
        private int availableItemStock;

        private OrderItemBuilder() {
        }

        public static OrderItemBuilder orderItem() {
            return new OrderItemBuilder();
        }

        @Override
        public OrderItem build() {
            return new OrderItem(this, Clock.system(ZoneId.systemDefault()));
        }

        public OrderItemBuilder withItemId(UUID itemId) {
            this.itemId = itemId;
            return this;
        }

        public OrderItemBuilder withItemPrice(Price itemPrice) {
            this.itemPrice = itemPrice;
            return this;
        }

        public OrderItemBuilder withOrderedAmount(int orderedAmount) {
            this.orderedAmount = orderedAmount;
            return this;
        }

        public OrderItemBuilder withShippingDateBasedOnAvailableItemStock(int availableItemStock) {
            this.availableItemStock = availableItemStock;
            return this;
        }
    }

}
