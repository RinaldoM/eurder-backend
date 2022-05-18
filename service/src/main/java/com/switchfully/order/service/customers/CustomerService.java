package com.switchfully.order.service.customers;

import com.switchfully.order.domain.customers.Customer;
import com.switchfully.order.domain.customers.CustomerRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.UUID;

@Named
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerValidator customerValidator;

    @Inject
    public CustomerService(CustomerRepository customerRepository, CustomerValidator customerValidator) {
        this.customerRepository = customerRepository;
        this.customerValidator = customerValidator;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Customer createCustomer(Customer customer) {
        if (!customerValidator.isValidForCreation(customer)) {
            customerValidator.throwInvalidStateException(customer, "creation");
        }
        return customerRepository.save(customer);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Customer> getAllCustomers() {
        return customerRepository.getAll();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Customer getCustomer(UUID id) {
        return customerRepository.get(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Customer updateCustomer(Customer customer) {
        if (!customerValidator.isValidForUpdating(customer)) {
            customerValidator.throwInvalidStateException(customer, "updating");
        }
        return customerRepository.update(customer);
    }
}
