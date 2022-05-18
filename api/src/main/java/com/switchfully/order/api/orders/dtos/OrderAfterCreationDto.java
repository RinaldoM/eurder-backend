package com.switchfully.order.api.orders.dtos;

public class OrderAfterCreationDto {

    private String orderId;
    private float totalPrice;

    public OrderAfterCreationDto() {
    }

    public OrderAfterCreationDto withOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public OrderAfterCreationDto withTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public float getTotalPrice() {
        return totalPrice;
    }
}
