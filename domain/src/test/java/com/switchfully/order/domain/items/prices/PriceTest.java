package com.switchfully.order.domain.items.prices;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class PriceTest {

    @Test
    void add() {
        Price price1 = Price.create(BigDecimal.valueOf(10));
        Price price2 = Price.create(BigDecimal.valueOf(10.50));

        Price priceCombined = Price.add(price1, price2);

        Assertions.assertThat(priceCombined.getAmount().equals(BigDecimal.valueOf(20.50)));
    }

    @Test
    void sameAs() {
        Price price1 = Price.create(BigDecimal.valueOf(10));
        Price price2 = Price.create(BigDecimal.valueOf(10));

        Assertions.assertThat(price1.sameAs(price2)).isTrue();
    }

}
