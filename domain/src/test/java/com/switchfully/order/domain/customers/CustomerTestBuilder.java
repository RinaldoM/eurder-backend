package com.switchfully.order.domain.customers;

import com.switchfully.order.domain.customers.Customer.CustomerBuilder;
import com.switchfully.order.domain.customers.addresses.Address;
import com.switchfully.order.domain.customers.emails.Email;
import com.switchfully.order.domain.customers.phonenumbers.PhoneNumber;
import com.switchfully.order.infrastructure.builder.Builder;

import java.util.UUID;

import static com.switchfully.order.domain.customers.addresses.AddressTestBuilder.anAddress;
import static com.switchfully.order.domain.customers.emails.EmailTestBuilder.anEmail;
import static com.switchfully.order.domain.customers.phonenumbers.PhoneNumberTestBuilder.aPhoneNumber;

public class CustomerTestBuilder extends Builder<Customer> {

    private CustomerBuilder customerBuilder;

    private CustomerTestBuilder(CustomerBuilder customerBuilder) {
        this.customerBuilder = customerBuilder;
    }

    public static CustomerTestBuilder anEmptyCustomer() {
        return new CustomerTestBuilder(CustomerBuilder.customer());
    }

    public static CustomerTestBuilder aCustomer() {
        return new CustomerTestBuilder(CustomerBuilder.customer()
                .withFirstname("Tom")
                .withLastname("Thompson")
                .withAddress(anAddress().build())
                .withEmail(anEmail().build())
                .withPhoneNumber(aPhoneNumber().build())
        );
    }

    @Override
    public Customer build() {
        return customerBuilder.build();
    }

    public CustomerTestBuilder withId(UUID id) {
        customerBuilder.withId(id);
        return this;
    }

    public CustomerTestBuilder withFirstname(String firstname) {
        customerBuilder.withFirstname(firstname);
        return this;
    }

    public CustomerTestBuilder withLastname(String lastname) {
        customerBuilder.withLastname(lastname);
        return this;
    }

    public CustomerTestBuilder withEmail(Email email) {
        customerBuilder.withEmail(email);
        return this;
    }

    public CustomerTestBuilder withAddress(Address address) {
        customerBuilder.withAddress(address);
        return this;
    }

    public CustomerTestBuilder withPhoneNumber(PhoneNumber phoneNumber) {
        customerBuilder.withPhoneNumber(phoneNumber);
        return this;
    }
}