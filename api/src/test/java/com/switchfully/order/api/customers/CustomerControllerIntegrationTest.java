package com.switchfully.order.api.customers;

import com.switchfully.order.ControllerIntegrationTest;
import com.switchfully.order.api.customers.addresses.AddressDto;
import com.switchfully.order.api.customers.emails.EmailDto;
import com.switchfully.order.api.customers.phonenumbers.PhoneNumberDto;
import com.switchfully.order.api.interceptors.ControllerExceptionHandler;
import com.switchfully.order.domain.customers.Customer;
import com.switchfully.order.domain.customers.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.inject.Inject;

import static com.switchfully.order.domain.customers.CustomerTestBuilder.aCustomer;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

class CustomerControllerIntegrationTest extends ControllerIntegrationTest {

    private static final String HTTP_LOCALHOST = "http://localhost";

    @Inject
    private CustomerRepository customerRepository;

    @Override
    public void clearDatabase() {
        customerRepository.getEntityManager().createQuery("DELETE FROM Customer").executeUpdate();
    }

    @Test
    void createCustomer() {
        CustomerDto createdCustomer = new TestRestTemplate()
                .postForObject(format(HTTP_LOCALHOST + ":%s/%s", getPort(), CustomerController.RESOURCE_NAME), createACustomer(), CustomerDto.class);

        assertCustomerIsEqualIgnoringId(createACustomer(), createdCustomer);
    }

    @Test
    void createCustomer_givenCustomerNotValidForCreationBecauseOfMissingFirstName_thenErrorObjectReturnedByControllerExceptionHandler() {
        CustomerDto customerToCreate = createACustomer().withFirstname(null);

        ControllerExceptionHandler.Error error = new TestRestTemplate()
                .postForObject(format(HTTP_LOCALHOST + ":%s/%s", getPort(), CustomerController.RESOURCE_NAME), customerToCreate, ControllerExceptionHandler.Error.class);

        assertThat(error).isNotNull();
        assertThat(error.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(error.getUniqueErrorId()).isNotNull().isNotEmpty();
        assertThat(error.getMessage()).contains("Invalid Customer provided for creation. " +
                "Provided object: Customer{id=");
    }

    @Test
    void updateCustomer() {
        Customer alreadyExistingCustomer = customerRepository.save(aCustomer().build());
        CustomerDto customerToUpdate = new CustomerDto()
                .withId(alreadyExistingCustomer.getId())
                .withFirstname(alreadyExistingCustomer.getFirstname())
                .withLastname("McDoodle")
                .withAddress(new AddressDto()
                        .withStreetName("Newstreet")
                        .withCountry("Newcountry")
                        .withHouseNumber("13")
                        .withPostalCode("3555"))
                .withEmail(new EmailDto()
                        .withComplete(alreadyExistingCustomer.getEmail().getComplete())
                        .withDomain(alreadyExistingCustomer.getEmail().getDomain())
                        .withLocalPart(alreadyExistingCustomer.getEmail().getLocalPart()))
                .withPhoneNumber(new PhoneNumberDto()
                        .withNumber(alreadyExistingCustomer.getPhoneNumber().getNumber())
                        .withCountryCallingCode(alreadyExistingCustomer.getPhoneNumber().getCountryCallingCode()));

        ResponseEntity<CustomerDto> result = new TestRestTemplate()
                .exchange(format(HTTP_LOCALHOST + ":%s/%s/%s", getPort(), CustomerController.RESOURCE_NAME, alreadyExistingCustomer.getId().toString()),
                        HttpMethod.PUT,
                        new HttpEntity<>(customerToUpdate),
                        CustomerDto.class);

        assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(customerToUpdate);
    }

    @Test
    void getAllCustomers_given2CreatedCustomers_whenGetAllCustomers_thenReturnAllCustomers() {
        new TestRestTemplate()
                .postForObject(format(HTTP_LOCALHOST + ":%s/%s", getPort(), CustomerController.RESOURCE_NAME),
                        createACustomer(), CustomerDto.class);
        new TestRestTemplate()
                .postForObject(format(HTTP_LOCALHOST + ":%s/%s", getPort(), CustomerController.RESOURCE_NAME),
                        createACustomer(), CustomerDto.class);

        CustomerOverviewDto[] allCustomers = new TestRestTemplate()
                .getForObject(format(HTTP_LOCALHOST + ":%s/%s", getPort(), CustomerController.RESOURCE_NAME), CustomerOverviewDto[].class);

        assertThat(allCustomers).hasSize(2);
    }

    @Test
    void getAllCustomers__assertResultIsCorrectlyReturned() {
        CustomerDto customerInDb = new TestRestTemplate()
                .postForObject(format(HTTP_LOCALHOST + ":%s/%s", getPort(), CustomerController.RESOURCE_NAME),
                        createACustomer(), CustomerDto.class);

        CustomerOverviewDto[] allCustomers = new TestRestTemplate()
                .getForObject(format(HTTP_LOCALHOST + ":%s/%s", getPort(), CustomerController.RESOURCE_NAME), CustomerOverviewDto[].class);

        assertThat(allCustomers).hasSize(1);
        assertThat(allCustomers[0])
                .usingRecursiveComparison()
                .ignoringFields("email", "address", "phoneNumber")
                .isEqualTo(customerInDb);
    }

    @Test
    void getCustomer_given3CreatedCustomers_whenGetSpecificCustomer_thenReturnOnlyThatCustomer() {
        new TestRestTemplate()
                .postForObject(format(HTTP_LOCALHOST + ":%s/%s", getPort(), CustomerController.RESOURCE_NAME),
                        createACustomer(), CustomerDto.class);
        CustomerDto customerToFind = new TestRestTemplate()
                .postForObject(format(HTTP_LOCALHOST + ":%s/%s", getPort(), CustomerController.RESOURCE_NAME),
                        createACustomer().withFirstname("Minion"), CustomerDto.class);
        new TestRestTemplate()
                .postForObject(format(HTTP_LOCALHOST + ":%s/%s", getPort(), CustomerController.RESOURCE_NAME),
                        createACustomer(), CustomerDto.class);

        CustomerDto foundCustomer = new TestRestTemplate()
                .getForObject(format(HTTP_LOCALHOST + ":%s/%s/%s", getPort(), CustomerController.RESOURCE_NAME, customerToFind.getId()), CustomerDto.class);

        assertThat(foundCustomer)
                .usingRecursiveComparison()
                .isEqualTo(customerToFind);
    }

    private CustomerDto createACustomer() {
        return new CustomerDto()
                .withFirstname("Bruce")
                .withLastname("Wayne")
                .withEmail(new EmailDto()
                        .withLocalPart("brucy")
                        .withDomain("bat.net")
                        .withComplete("brucy@bat.net"))
                .withPhoneNumber(new PhoneNumberDto()
                        .withNumber("485212121")
                        .withCountryCallingCode("+32"))
                .withAddress(new AddressDto()
                        .withStreetName("Secretstreet")
                        .withHouseNumber("841")
                        .withPostalCode("1238")
                        .withCountry("GothamCountry"));
    }

    private void assertCustomerIsEqualIgnoringId(CustomerDto customerToCreate, CustomerDto createdCustomer) {
        assertThat(createdCustomer.getId()).isNotNull().isNotEmpty();
        assertThat(createdCustomer.getAddress()).isEqualToComparingFieldByField(customerToCreate.getAddress());
        assertThat(createdCustomer.getPhoneNumber()).isEqualToComparingFieldByField(customerToCreate.getPhoneNumber());
        assertThat(createdCustomer.getEmail()).isEqualToComparingFieldByField(customerToCreate.getEmail());
        assertThat(createdCustomer.getFirstname()).isEqualTo(customerToCreate.getFirstname());
        assertThat(createdCustomer.getLastname()).isEqualTo(customerToCreate.getLastname());
    }

}
