package com.switchfully.order.api.items;

import com.switchfully.order.domain.items.Item;
import com.switchfully.order.domain.items.prices.Price;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static com.switchfully.order.domain.items.Item.ItemBuilder.item;
import static org.assertj.core.api.Assertions.assertThat;

class ItemOverviewMapperTest {

    @Test
    void toOverviewDto() {
        UUID itemId = UUID.randomUUID();
        Item item = item()
                .withId(itemId)
                .withName("Half-Life 3")
                .withDescription("Boehoehoehoeeeee")
                .withPrice(Price.create(BigDecimal.valueOf(49.50)))
                .withAmountOfStock(50520)
                .build();

        ItemOverviewDto itemOverviewDto = new ItemOverviewMapper().toDto(item);

        assertThat(itemOverviewDto)
                .isEqualToComparingFieldByField(new ItemOverviewDto()
                        .withId(itemId)
                        .withName("Half-Life 3")
                        .withPrice(49.50f)
                        .withAmountOfStock(50520)
                        .withStockUrgency(item.getStockUrgency().name()));
    }

}
