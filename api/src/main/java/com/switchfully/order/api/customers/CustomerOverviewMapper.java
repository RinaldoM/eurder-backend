package com.switchfully.order.api.customers;

import com.switchfully.order.domain.customers.Customer;
import com.switchfully.order.infrastructure.dto.Mapper;

import javax.inject.Named;

@Named
public class CustomerOverviewMapper extends Mapper<CustomerOverviewDto, Customer> {

    @Override
    public CustomerOverviewDto toDto(Customer customer) {
        return new CustomerOverviewDto()
                .withId(customer.getId())
                .withFirstname(customer.getFirstname())
                .withLastname(customer.getLastname());
    }

    @Override
    public Customer toDomain(CustomerOverviewDto customerOverviewDto) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
