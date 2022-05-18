package com.switchfully.order.service.items;

import com.switchfully.order.domain.items.Item;
import com.switchfully.order.domain.items.ItemRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.UUID;

@Named
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

    @Inject
    public ItemService(ItemRepository itemRepository, ItemValidator itemValidator) {
        this.itemRepository = itemRepository;
        this.itemValidator = itemValidator;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Item createItem(Item item) {
        if (!itemValidator.isValidForCreation(item)) {
            itemValidator.throwInvalidStateException(item, "creation");
        }
        return itemRepository.save(item);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Item updateItem(Item item) {
        if (!itemValidator.isValidForUpdating(item)) {
            itemValidator.throwInvalidStateException(item, "updating");
        }
        return itemRepository.update(item);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Item getItem(UUID itemId) {
        return itemRepository.get(itemId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void decrementStockForItem(UUID itemId, int amountToDecrement) {
        Item item = itemRepository.get(itemId);
        item.decrementStock(amountToDecrement);
        itemRepository.update(item);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Item> getAllItems() {
        return itemRepository.getAll();
    }
}
