package com.switchfully.order.service.customers;

import com.switchfully.order.domain.customers.Customer;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.switchfully.order.domain.customers.CustomerTestBuilder.aCustomer;
import static org.assertj.core.api.Assertions.assertThat;

class CustomerValidatorTest {

    @Test
    void isValidForCreation_happyPath() {
        assertThat(new CustomerValidator()
                .isValidForCreation(aCustomer()
                        .build()))
                .isTrue();
    }

    @Test
    void isValidForCreation_givenAnId_thenNotValidForCreation() {
        assertThat(new CustomerValidator()
                .isValidForCreation(aCustomer()
                        .withId(UUID.randomUUID())
                        .build()))
                .isFalse();
    }

    /**
     * To have this code properly tested,
     * we should create a test for each individual empty or null value.
     * However, since this is not an application intended for production, we didn't.
     * Check the ItemValidatorTest for a better example.
     */
    @Test
    void isValidForCreation_givenSomeMissingValues_thenNotValidForCreation() {
        assertThat(new CustomerValidator()
                .isValidForCreation(aCustomer()
                        .withId(UUID.randomUUID())
                        .withFirstname("")
                        .withLastname(null)
                        .withEmail(null)
                        .build()))
                .isFalse();
    }

    @Test
    void isValidForUpdating_happyPath() {
        Customer customer = aCustomer().withId(UUID.randomUUID()).build();

        assertThat(new CustomerValidator()
                .isValidForUpdating(customer))
                .isTrue();
    }

    @Test
    void isValidForUpdating_givenNoId_thenNotValidForUpdating() {
        assertThat(new CustomerValidator()
                .isValidForUpdating(aCustomer().build()))
                .isFalse();
    }

}
