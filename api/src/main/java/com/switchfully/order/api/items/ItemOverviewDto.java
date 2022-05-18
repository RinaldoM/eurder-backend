package com.switchfully.order.api.items;

import java.util.UUID;

public class ItemOverviewDto {

    private String id;
    private String name;
    private float price;
    private int amountOfStock;
    private String stockUrgency;

    public ItemOverviewDto() {
    }

    public ItemOverviewDto withId(UUID id) {
        this.id = id.toString();
        return this;
    }

    public ItemOverviewDto withoutId() {
        this.id = null;
        return this;
    }

    public ItemOverviewDto withName(String name) {
        this.name = name;
        return this;
    }

    public ItemOverviewDto withPrice(float price) {
        this.price = price;
        return this;
    }

    public ItemOverviewDto withStockUrgency(String stockUrgency) {
        this.stockUrgency = stockUrgency;
        return this;
    }

    public ItemOverviewDto withAmountOfStock(int amountOfStock) {
        this.amountOfStock = amountOfStock;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public int getAmountOfStock() {
        return amountOfStock;
    }

    public String getStockUrgency() {
        return stockUrgency;
    }
}
