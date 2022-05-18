package com.switchfully.order.api.orders.dtos.reports;

import java.util.List;

public class OrdersReportDto {

    private float totalPriceOfAllOrders;
    private List<SingleOrderReportDto> orders;

    public OrdersReportDto() {
    }

    public OrdersReportDto withTotalPriceOfAllOrders(float totalPriceOfAllOrders) {
        this.totalPriceOfAllOrders = totalPriceOfAllOrders;
        return this;
    }

    public OrdersReportDto withOrders(List<SingleOrderReportDto> orders) {
        this.orders = orders;
        return this;
    }

    public float getTotalPriceOfAllOrders() {
        return totalPriceOfAllOrders;
    }

    public List<SingleOrderReportDto> getOrders() {
        return orders;
    }
}
