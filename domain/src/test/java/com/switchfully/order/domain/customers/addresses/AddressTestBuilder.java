package com.switchfully.order.domain.customers.addresses;

import com.switchfully.order.domain.customers.addresses.Address.AddressBuilder;
import com.switchfully.order.infrastructure.builder.Builder;

public class AddressTestBuilder extends Builder<Address> {

    private AddressBuilder addressBuilder;

    private AddressTestBuilder(AddressBuilder addressBuilder) {
        this.addressBuilder = addressBuilder;
    }

    public static AddressTestBuilder anEmptyAddress() {
        return new AddressTestBuilder(AddressBuilder.address());
    }

    public static AddressTestBuilder anAddress() {
        return new AddressTestBuilder(AddressBuilder.address()
                .withCountry("Belgium")
                .withHouseNumber("16A")
                .withPostalCode("3000")
                .withStreetName("Jantjesstraat"));
    }

    @Override
    public Address build() {
        return addressBuilder.build();
    }

    public AddressTestBuilder withStreetName(String streetName) {
        addressBuilder.withStreetName(streetName);
        return this;
    }

    public AddressTestBuilder withHouseNumber(String houseNumber) {
        addressBuilder.withHouseNumber(houseNumber);
        return this;
    }

    public AddressTestBuilder withPostalCode(String postalCode) {
        addressBuilder.withPostalCode(postalCode);
        return this;
    }

    public AddressTestBuilder withCountry(String country) {
        addressBuilder.withCountry(country);
        return this;
    }
}