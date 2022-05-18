package com.switchfully.order.api.orders.dtos;

import com.switchfully.order.api.customers.addresses.AddressDto;

import java.util.Arrays;
import java.util.List;

public class OrderDto {

    private String orderId;
    private List<ItemGroupDto> itemGroups;
    private AddressDto address;

    public OrderDto() {
    }

    public OrderDto withItemGroups(ItemGroupDto... itemGroups) {
        this.itemGroups = Arrays.asList(itemGroups);
        return this;
    }

    public OrderDto withAddress(AddressDto address) {
        this.address = address;
        return this;
    }

    public OrderDto withOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public List<ItemGroupDto> getItemGroups() {
        return itemGroups;
    }

    public AddressDto getAddress() {
        return address;
    }

    public String getOrderId() {
        return orderId;
    }
}
