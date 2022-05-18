package com.switchfully.order.api.customers;

import com.switchfully.order.service.customers.CustomerService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/" + CustomerController.RESOURCE_NAME)
public class CustomerController {

    public static final String RESOURCE_NAME = "customers";

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    private final CustomerOverviewMapper customerOverviewMapper;

    @Inject
    public CustomerController(CustomerService customerService, CustomerMapper customerMapper, CustomerOverviewMapper customerOverviewMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
        this.customerOverviewMapper = customerOverviewMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerDto createCustomer(@RequestBody CustomerDto customerDto) {
        return customerMapper.toDto(
                customerService.createCustomer(
                        customerMapper.toDomain(customerDto)));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerDto updateCustomer(@PathVariable String id, @RequestBody CustomerDto customerDto) {
        return customerMapper.toDto(
                customerService.updateCustomer(
                        customerMapper.toDomain(UUID.fromString(id), customerDto)));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerOverviewDto> getAllCustomers() {
        return customerService.getAllCustomers().stream()
                .map(customerOverviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerDto getCustomer(@PathVariable String id) {
        return customerMapper.toDto(
                customerService.getCustomer(UUID.fromString(id)));
    }

}
