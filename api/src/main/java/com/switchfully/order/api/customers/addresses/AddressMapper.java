package com.switchfully.order.api.customers.addresses;

import com.switchfully.order.domain.customers.addresses.Address;
import com.switchfully.order.domain.customers.addresses.Address.AddressBuilder;
import com.switchfully.order.infrastructure.dto.Mapper;

import javax.inject.Named;

@Named
public class AddressMapper extends Mapper<AddressDto, Address> {

    @Override
    public AddressDto toDto(Address address) {
        return new AddressDto()
                .withStreetName(address.getStreetName())
                .withHouseNumber(address.getHouseNumber())
                .withPostalCode(address.getPostalCode())
                .withCountry(address.getCountry());
    }

    @Override
    public Address toDomain(AddressDto addressDto) {
        return AddressBuilder.address()
                .withStreetName(addressDto.getStreetName())
                .withHouseNumber(addressDto.getHouseNumber())
                .withPostalCode(addressDto.getPostalCode())
                .withCountry(addressDto.getCountry())
                .build();
    }
}
