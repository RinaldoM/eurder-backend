package com.switchfully.order.domain.items;

import com.switchfully.order.domain.items.Item.ItemBuilder;
import com.switchfully.order.domain.items.prices.Price;
import com.switchfully.order.infrastructure.builder.Builder;

import java.util.UUID;

import static com.switchfully.order.domain.items.Item.ItemBuilder.item;
import static java.math.BigDecimal.valueOf;

public class ItemTestBuilder extends Builder<Item>{

    private ItemBuilder itemBuilder;

    private ItemTestBuilder(ItemBuilder itemBuilder) {
        this.itemBuilder = itemBuilder;
    }

    public static ItemTestBuilder anEmptyItem() {
        return new ItemTestBuilder(item());
    }

    public static ItemTestBuilder anItem() {
        return new ItemTestBuilder(item()
            .withName("Headphone")
            .withDescription("Just a simple headphone")
            .withAmountOfStock(50)
            .withPrice(Price.create(valueOf(49.95))));
    }

    @Override
    public Item build() {
        return itemBuilder.build();
    }

    public ItemTestBuilder withId(UUID id) {
        itemBuilder.withId(id);
        return this;
    }

    public ItemTestBuilder withName(String name) {
        itemBuilder.withName(name);
        return this;
    }

    public ItemTestBuilder withDescription(String description) {
        itemBuilder.withDescription(description);
        return this;
    }

    public ItemTestBuilder withPrice(Price price) {
        itemBuilder.withPrice(price);
        return this;
    }

    public ItemTestBuilder withAmountOfStock(int amountOfStock) {
        itemBuilder.withAmountOfStock(amountOfStock);
        return this;
    }
}