package com.switchfully.order.api.items;

import com.switchfully.order.domain.items.Item.StockUrgency;
import com.switchfully.order.service.items.ItemService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/" + ItemController.RESOURCE_NAME)
public class ItemController {

    public static final String RESOURCE_NAME = "items";

    private final ItemService itemService;
    private final ItemMapper itemMapper;
    private final ItemOverviewMapper itemOverviewMapper;

    @Inject
    public ItemController(ItemService itemService, ItemMapper itemMapper, ItemOverviewMapper itemOverviewMapper) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
        this.itemOverviewMapper = itemOverviewMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ItemDto createItem(@RequestBody ItemDto itemDto) {
        return itemMapper.toDto(
                itemService.createItem(
                        itemMapper.toDomain(itemDto)));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ItemDto updateItem(@PathVariable String id, @RequestBody ItemDto itemDto) {
        return itemMapper.toDto(
                itemService.updateItem(
                        itemMapper.toDomain(UUID.fromString(id), itemDto)));
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ItemDto getItem(@PathVariable String id) {
        return itemMapper.toDto(
                itemService.getItem(UUID.fromString(id)));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ItemOverviewDto> getAllItems(@RequestParam(name = "stockUrgency", required = false) String stockUrgency) {
        List<ItemOverviewDto> allItems = itemService.getAllItems().stream()
                .map(itemOverviewMapper::toDto)
                .sorted(Comparator.comparingInt(ItemOverviewDto::getAmountOfStock))
                .collect(Collectors.toList());
        return filterOnStockUrgency(stockUrgency, allItems);
    }

    private List<ItemOverviewDto> filterOnStockUrgency(String stockUrgency, List<ItemOverviewDto> allItems) {
        if (stockUrgency != null) {
            StockUrgency stockUrgencyToFilterOn = StockUrgency.valueOf(stockUrgency);
            return allItems.stream()
                    .filter(item -> StockUrgency.valueOf(item.getStockUrgency()).equals(stockUrgencyToFilterOn))
                    .collect(Collectors.toList());
        } else {
            return allItems;
        }
    }

}
