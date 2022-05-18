package com.switchfully.order.api.orders.dtos.reports;

import java.util.List;

public class SingleOrderReportDto {

    private List<ItemGroupReportDto> itemGroups;
    private String orderId;

    public SingleOrderReportDto() {
    }

    public SingleOrderReportDto withItemGroups(List<ItemGroupReportDto> itemGroups) {
        this.itemGroups = itemGroups;
        return this;
    }

    public SingleOrderReportDto withOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public List<ItemGroupReportDto> getItemGroups() {
        return itemGroups;
    }

    public String getOrderId() {
        return orderId;
    }
}
