package com.switchfully.order.domain.orders;

import com.switchfully.order.domain.items.prices.Price;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.switchfully.order.domain.orders.orderitems.OrderItemTestBuilder.anOrderItem;
import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {

    @Test
    void getTotalPrice_givenOrderWithOrderItems_thenTotalPriceIsSumOfPricesOfOrderItemsMultipliedByOrderedAmount() {
        Order order = OrderTestBuilder.anOrder()
                .withOrderItems(anOrderItem().withOrderedAmount(2).withItemPrice(Price.create(BigDecimal.valueOf(40.50))).build(),
                        anOrderItem().withOrderedAmount(1).withItemPrice(Price.create(BigDecimal.valueOf(60.50))).build(),
                        anOrderItem().withOrderedAmount(10).withItemPrice(Price.create(BigDecimal.valueOf(25))).build())
                .build();

        Price totalPrice = order.getTotalPrice();

        BigDecimal totalPriceOfFirstItem = new BigDecimal(40.50).multiply(BigDecimal.valueOf(2));
        BigDecimal totalPriceOfSecondItem = new BigDecimal(60.50).multiply(BigDecimal.valueOf(1));
        BigDecimal totalPriceOfThirdItem = new BigDecimal(25).multiply(BigDecimal.valueOf(10));
        assertThat(totalPrice.getAmount()
                .equals(totalPriceOfFirstItem.add(totalPriceOfSecondItem).add(totalPriceOfThirdItem)))
                .isTrue();
    }

}
