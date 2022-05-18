package com.switchfully.order.api.orders.dtos;

public class ItemGroupDto {

    private String itemId;
    private int orderedAmount;

    public ItemGroupDto() {
    }

    public ItemGroupDto withItemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    public ItemGroupDto withOrderedAmount(int orderedAmount) {
        this.orderedAmount = orderedAmount;
        return this;
    }

    public String getItemId() {
        return itemId;
    }

    public int getOrderedAmount() {
        return orderedAmount;
    }
}
