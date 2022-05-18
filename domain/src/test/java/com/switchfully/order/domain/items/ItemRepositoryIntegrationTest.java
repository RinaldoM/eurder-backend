package com.switchfully.order.domain.items;

import com.switchfully.order.IntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

import static com.switchfully.order.domain.items.ItemTestBuilder.anItem;

class ItemRepositoryIntegrationTest extends IntegrationTest {

    @Inject
    private ItemRepository repository;

    @Test
    void save() {
        Item itemToSave = anItem().build();

        Item savedItem = repository.save(itemToSave);

        Assertions.assertThat(savedItem.getId()).isNotNull();
        Assertions.assertThat(repository.get(savedItem.getId()))
                .isEqualToComparingFieldByField(savedItem);
    }

    @Test
    void update() {
        Item itemToSave = anItem().withAmountOfStock(10).build();
        Item savedItem = repository.save(itemToSave);

        savedItem.decrementStock(2);
        Item updatedItem = repository.update(savedItem);

        Assertions.assertThat(updatedItem.getId()).isNotNull().isEqualTo(savedItem.getId());
        Assertions.assertThat(updatedItem.getAmountOfStock()).isEqualTo(8);
        Assertions.assertThat(repository.getAll()).hasSize(1);
    }

    @Test
    void get() {
        Item savedItem = repository.save(anItem().build());

        Item actualItem = repository.get(savedItem.getId());

        Assertions.assertThat(actualItem)
                .isEqualToComparingFieldByField(savedItem);
    }

    @Test
    void getAll() {
        Item itemOne = repository.save(anItem().build());
        Item itemTwo = repository.save(anItem().build());

        List<Item> allItems = repository.getAll();

        Assertions.assertThat(allItems)
                .containsExactlyInAnyOrder(itemOne, itemTwo);
    }

}
