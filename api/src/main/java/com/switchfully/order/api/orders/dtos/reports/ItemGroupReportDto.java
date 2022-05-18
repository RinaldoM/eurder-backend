package com.switchfully.order.api.orders.dtos.reports;

public class ItemGroupReportDto {

    private String itemId;
    private String name;
    private int orderedAmount;
    private float totalPrice;

    public ItemGroupReportDto() {
    }

    public ItemGroupReportDto withItemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    public ItemGroupReportDto withOrderedAmount(int orderedAmount) {
        this.orderedAmount = orderedAmount;
        return this;
    }

    public ItemGroupReportDto withName(String name) {
        this.name = name;
        return this;
    }

    public ItemGroupReportDto withTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public String getItemId() {
        return itemId;
    }

    public int getOrderedAmount() {
        return orderedAmount;
    }

    public String getName() {
        return name;
    }

    public float getTotalPrice() {
        return totalPrice;
    }
}
