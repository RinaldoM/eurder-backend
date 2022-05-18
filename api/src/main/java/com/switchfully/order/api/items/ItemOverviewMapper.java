package com.switchfully.order.api.items;

import com.switchfully.order.domain.items.Item;
import com.switchfully.order.infrastructure.dto.Mapper;

import javax.inject.Named;

@Named
public class ItemOverviewMapper extends Mapper<ItemOverviewDto, Item> {

    @Override
    public ItemOverviewDto toDto(Item item) {
        return new ItemOverviewDto()
                .withId(item.getId())
                .withName(item.getName())
                .withAmountOfStock(item.getAmountOfStock())
                .withPrice(item.getPrice().getAmount().floatValue())
                .withStockUrgency(item.getStockUrgency().name());
    }

    @Override
    public Item toDomain(ItemOverviewDto dtoObject) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
