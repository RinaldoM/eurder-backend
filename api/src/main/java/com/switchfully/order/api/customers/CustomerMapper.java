package com.switchfully.order.api.customers;

import com.switchfully.order.api.customers.addresses.AddressMapper;
import com.switchfully.order.api.customers.emails.EmailMapper;
import com.switchfully.order.api.customers.phonenumbers.PhoneNumberMapper;
import com.switchfully.order.domain.customers.Customer;
import com.switchfully.order.infrastructure.dto.Mapper;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@Named
public class CustomerMapper extends Mapper<CustomerDto, Customer> {

    private final AddressMapper addressMapper;
    private final EmailMapper emailMapper;
    private final PhoneNumberMapper phoneNumberMapper;

    @Inject
    public CustomerMapper(AddressMapper addressMapper, EmailMapper emailMapper, PhoneNumberMapper phoneNumberMapper) {
        this.addressMapper = addressMapper;
        this.emailMapper = emailMapper;
        this.phoneNumberMapper = phoneNumberMapper;
    }

    public Customer toDomain(UUID customerId, CustomerDto customerDto) {
        if(customerDto.getId() == null) {
            return toDomain(customerDto.withId(customerId));
        }
        if(!customerId.toString().equals(customerDto.getId())) {
            throw new IllegalArgumentException("When updating a customer, the provided ID in the path should match the ID in the body: " +
                    "ID in path = " + customerId.toString() + ", ID in body = " + customerDto.getId());
        }
        return toDomain(customerDto);
    }

    @Override
    public CustomerDto toDto(Customer customer) {
        return new CustomerDto()
                .withId(customer.getId())
                .withFirstname(customer.getFirstname())
                .withLastname(customer.getLastname())
                .withAddress(addressMapper.toDto(customer.getAddress()))
                .withEmail(emailMapper.toDto(customer.getEmail()))
                .withPhoneNumber(phoneNumberMapper.toDto(customer.getPhoneNumber()));
    }

    @Override
    public Customer toDomain(CustomerDto customerDto) {
        return Customer.CustomerBuilder.customer()
                .withId(customerDto.getId() == null ? null : UUID.fromString(customerDto.getId()))
                .withLastname(customerDto.getLastname())
                .withFirstname(customerDto.getFirstname())
                .withAddress(addressMapper.toDomain(customerDto.getAddress()))
                .withEmail(emailMapper.toDomain(customerDto.getEmail()))
                .withPhoneNumber(phoneNumberMapper.toDomain(customerDto.getPhoneNumber()))
                .build();
    }

}
