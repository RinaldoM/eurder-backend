package com.switchfully.order.api.items;

import com.switchfully.order.domain.items.Item;
import com.switchfully.order.domain.items.prices.Price;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static com.switchfully.order.domain.items.Item.ItemBuilder.item;
import static org.assertj.core.api.Assertions.assertThat;

class ItemMapperTest {

    @Test
    void toDto() {
        UUID itemId = UUID.randomUUID();
        Item item = item()
                .withId(itemId)
                .withName("Half-Life 3")
                .withDescription("Boehoehoehoeeeee")
                .withPrice(Price.create(BigDecimal.valueOf(49.50)))
                .withAmountOfStock(50520)
                .build();

        ItemDto itemDto = new ItemMapper().toDto(item);

        assertThat(itemDto)
                .isEqualToComparingFieldByField(new ItemDto()
                        .withId(itemId)
                        .withName("Half-Life 3")
                        .withDescription("Boehoehoehoeeeee")
                        .withPrice(49.50f)
                        .withAmountOfStock(50520)
                        .withStockUrgency(item.getStockUrgency().name()));
    }

    @Test
    void toDomainWithId() {
        UUID itemId = UUID.randomUUID();

        Item item = new ItemMapper().toDomain(itemId, new ItemDto()
                .withId(itemId)
                .withName("Half-Life 3")
                .withDescription("Boehoehoe")
                .withPrice(45f)
                .withAmountOfStock(50520));

        assertThat(item)
                .usingRecursiveComparison().isEqualTo(item()
                        .withId(itemId)
                        .withName("Half-Life 3")
                        .withDescription("Boehoehoe")
                        .withPrice(Price.create(BigDecimal.valueOf(45.0)))
                        .withAmountOfStock(50520)
                        .build());
    }

    @Test
    void toDomainWithId_givenIdNotMatchingIdInDto_thenThrowException() {
        UUID itemIdPath = UUID.randomUUID();
        UUID itemIdBody = UUID.randomUUID();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new ItemMapper().toDomain(itemIdPath, new ItemDto().withId(itemIdBody)))
                .withMessage("When updating an item, the provided ID in the path should match the " +
                        "ID in the body: ID in path = " + itemIdPath.toString() + ", ID in body = " + itemIdBody.toString());
    }

    @Test
    void toDomainWithId_givenIdButIdInBodyIsNull_thenUseGivenId() {
        UUID itemId = UUID.randomUUID();

        Item item = new ItemMapper().toDomain(itemId, new ItemDto()
                .withoutId()
                .withName("Half-Life 3")
                .withDescription("Boehoehoe")
                .withPrice(45f)
                .withAmountOfStock(50520));

        assertThat(item)
                .usingRecursiveComparison()
                .isEqualTo(item()
                        .withId(itemId)
                        .withName("Half-Life 3")
                        .withDescription("Boehoehoe")
                        .withPrice(Price.create(BigDecimal.valueOf(45.0)))
                        .withAmountOfStock(50520)
                        .build());
    }

    @Test
    void toDomain() {
        Item item = new ItemMapper().toDomain(new ItemDto()
                .withName("Half-Life 3")
                .withDescription("Boehoehoe")
                .withPrice(45f)
                .withAmountOfStock(50520));

        assertThat(item)
                .usingRecursiveComparison()
                .isEqualTo(item()
                        .withName("Half-Life 3")
                        .withDescription("Boehoehoe")
                        .withPrice(Price.create(BigDecimal.valueOf(45.0)))
                        .withAmountOfStock(50520)
                        .build());
    }

    @Test
    void toDomain_givenId_thenAlsoCorrectlyMapId() {
        UUID id = UUID.randomUUID();
        Item item = new ItemMapper().toDomain(new ItemDto()
                .withId(id)
                .withName("Half-Life 3")
                .withDescription("Boehoehoe")
                .withPrice(45f)
                .withAmountOfStock(50520));

        assertThat(item)
                .usingRecursiveComparison()
                .isEqualTo(item()
                        .withId(id)
                        .withName("Half-Life 3")
                        .withDescription("Boehoehoe")
                        .withPrice(Price.create(BigDecimal.valueOf(45.0)))
                        .withAmountOfStock(50520)
                        .build());
    }

}
