package com.switchfully.order.api.customers.addresses;

import com.switchfully.order.domain.customers.addresses.Address;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.switchfully.order.domain.customers.addresses.Address.AddressBuilder.address;

class AddressMapperTest {

    @Test
    void toDto() {
        AddressDto addressDto = new AddressMapper().toDto(address()
                .withStreetName("Teststraat")
                .withHouseNumber("88B")
                .withPostalCode("3000")
                .withCountry("Belgium")
                .build());

        Assertions.assertThat(addressDto)
                .isEqualToComparingFieldByField(new AddressDto()
                        .withStreetName("Teststraat")
                        .withHouseNumber("88B")
                        .withPostalCode("3000")
                        .withCountry("Belgium"));
    }

    @Test
    void toDomain() {
        Address address = new AddressMapper().toDomain(new AddressDto()
                .withStreetName("Teststraat")
                .withHouseNumber("88B")
                .withPostalCode("3000")
                .withCountry("Belgium"));

        Assertions.assertThat(address)
                .isEqualToComparingFieldByField(address()
                        .withStreetName("Teststraat")
                        .withHouseNumber("88B")
                        .withPostalCode("3000")
                        .withCountry("Belgium")
                        .build());
    }

}
