package com.switchfully.order.domain.items;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.switchfully.order.domain.items.ItemTestBuilder.anItem;
import static org.assertj.core.api.Assertions.assertThat;

class ItemTest {

    @Test
    void decrementStock() {
        Item item = anItem().withAmountOfStock(10).build();
        item.decrementStock(8);
        assertThat(item.getAmountOfStock()).isEqualTo(2);
    }

    @Test
    void decrementStock_givenHigherAmountToDecrementThanActualRemainingStock_thenThrowException() {
        UUID itemId = UUID.randomUUID();
        Item item = anItem().withId(itemId).withAmountOfStock(7).build();

        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> item.decrementStock(8))
                .withMessage("Decrementing the stock amount of an item " + itemId.toString() + " below 0 is not allowed");
    }

    @Test
    void getStockUrgency_givenAmountOfStockLowerThan5_thenLowStockUrgency() {
        Item item = anItem().withAmountOfStock(4).build();
        assertThat(item.getStockUrgency()).isEqualTo(Item.StockUrgency.STOCK_LOW);
    }

    @Test
    void getStockUrgency_givenAmountOfStockLowerThan10_thenMediumStockUrgency() {
        Item item = anItem().withAmountOfStock(7).build();
        assertThat(item.getStockUrgency()).isEqualTo(Item.StockUrgency.STOCK_MEDIUM);
    }

    @Test
    void getStockUrgency_givenAmountOfStockEqualTo10_thenHighStockUrgency() {
        Item item = anItem().withAmountOfStock(10).build();
        assertThat(item.getStockUrgency()).isEqualTo(Item.StockUrgency.STOCK_HIGH);
    }

    @Test
    void getStockUrgency_givenAmountOfStockHigherThan10_thenHighStockUrgency() {
        Item item = anItem().withAmountOfStock(11).build();
        assertThat(item.getStockUrgency()).isEqualTo(Item.StockUrgency.STOCK_HIGH);
    }


}
