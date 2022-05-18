package com.switchfully.order.api.customers;

import com.switchfully.order.domain.customers.Customer;
import com.switchfully.order.domain.customers.CustomerTestBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class CustomerOverviewMapperTest {

    @Test
    void toDto() {
        UUID id = UUID.randomUUID();
        Customer customer = CustomerTestBuilder.aCustomer()
                .withId(id)
                .withFirstname("Jimmy")
                .withLastname("Moriati")
                .build();

        CustomerOverviewDto customerOverviewDto = new CustomerOverviewMapper().toDto(customer);

        Assertions.assertThat(customerOverviewDto)
                .isEqualToComparingFieldByField(
                        new CustomerOverviewDto()
                                .withId(id)
                                .withFirstname("Jimmy")
                                .withLastname("Moriati"));


    }
}
