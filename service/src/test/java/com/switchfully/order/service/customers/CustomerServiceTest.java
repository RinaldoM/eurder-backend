package com.switchfully.order.service.customers;

import com.switchfully.order.domain.customers.Customer;
import com.switchfully.order.domain.customers.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.switchfully.order.domain.customers.CustomerTestBuilder.aCustomer;

class CustomerServiceTest {

    private CustomerService customerService;
    private CustomerValidator customerValidatorMock;
    private CustomerRepository customerRepositoryMock;

    @BeforeEach
    void setupService() {
        customerRepositoryMock = Mockito.mock(CustomerRepository.class);
        customerValidatorMock = Mockito.mock(CustomerValidator.class);
        customerService = new CustomerService(customerRepositoryMock, customerValidatorMock);
    }

    @Test
    void createCustomer_happyPath() {
        Customer customer = aCustomer().build();
        Mockito.when(customerValidatorMock.isValidForCreation(customer)).thenReturn(true);
        Mockito.when(customerRepositoryMock.save(customer)).thenReturn(customer);

        Customer createdCustomer = customerService.createCustomer(customer);

        Assertions.assertThat(createdCustomer).isNotNull();
    }

    @Test
    void createCustomer_givenCustomerThatIsNotValidForCreation_thenThrowException() {
        Customer customer = aCustomer().build();
        Mockito.when(customerValidatorMock.isValidForCreation(customer)).thenReturn(false);
        Mockito.doThrow(IllegalStateException.class).when(customerValidatorMock)
                .throwInvalidStateException(customer, "creation");

        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> customerService.createCustomer(customer));
    }

}
