package com.switchfully.order.api.customers;

import com.switchfully.order.api.customers.addresses.AddressDto;
import com.switchfully.order.api.customers.addresses.AddressMapper;
import com.switchfully.order.api.customers.emails.EmailDto;
import com.switchfully.order.api.customers.emails.EmailMapper;
import com.switchfully.order.api.customers.phonenumbers.PhoneNumberDto;
import com.switchfully.order.api.customers.phonenumbers.PhoneNumberMapper;
import com.switchfully.order.domain.customers.Customer;
import com.switchfully.order.domain.customers.addresses.Address;
import com.switchfully.order.domain.customers.addresses.AddressTestBuilder;
import com.switchfully.order.domain.customers.emails.Email;
import com.switchfully.order.domain.customers.emails.EmailTestBuilder;
import com.switchfully.order.domain.customers.phonenumbers.PhoneNumber;
import com.switchfully.order.domain.customers.phonenumbers.PhoneNumberTestBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static com.switchfully.order.domain.customers.addresses.AddressTestBuilder.anAddress;
import static com.switchfully.order.domain.customers.emails.EmailTestBuilder.anEmail;
import static com.switchfully.order.domain.customers.phonenumbers.PhoneNumberTestBuilder.aPhoneNumber;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerMapperTest {

    @Mock
    private AddressMapper addressMapperMock;

    @Mock
    private PhoneNumberMapper phoneNumberMapperMock;

    @Mock
    private EmailMapper emailMapperMock;

    @InjectMocks
    private CustomerMapper customerMapper;

    @Test
    void toDto() {
        // given
        AddressDto addressDto = new AddressDto();
        EmailDto emailDto = new EmailDto();
        PhoneNumberDto phoneNumberDto = new PhoneNumberDto();

        Address address = anAddress().build();
        Email email = anEmail().build();
        PhoneNumber phoneNumber = aPhoneNumber().build();

        when(addressMapperMock.toDto(address)).thenReturn(addressDto);
        when(emailMapperMock.toDto(email)).thenReturn(emailDto);
        when(phoneNumberMapperMock.toDto(phoneNumber)).thenReturn(phoneNumberDto);

        UUID customerId = UUID.randomUUID();
        Customer customer = Customer.CustomerBuilder.customer()
                .withId(customerId)
                .withFirstname("Koen")
                .withLastname("Kasteels")
                .withAddress(address)
                .withEmail(email)
                .withPhoneNumber(phoneNumber)
                .build();

        // when
        CustomerDto customerDto = customerMapper.toDto(customer);

        // then
        Assertions.assertThat(customerDto)
                .isEqualToComparingFieldByField(new CustomerDto()
                        .withId(customerId)
                        .withFirstname("Koen")
                        .withLastname("Kasteels")
                        .withAddress(addressDto)
                        .withEmail(emailDto)
                        .withPhoneNumber(phoneNumberDto));
        Mockito.verify(addressMapperMock).toDto(address);
        Mockito.verify(emailMapperMock).toDto(email);
        Mockito.verify(phoneNumberMapperMock).toDto(phoneNumber);

    }

    @Test
    void toDomain() {
        // given
        AddressDto addressDto = new AddressDto();
        EmailDto emailDto = new EmailDto();
        PhoneNumberDto phoneNumberDto = new PhoneNumberDto();

        Address address = AddressTestBuilder.anAddress().build();
        Email email = EmailTestBuilder.anEmail().build();
        PhoneNumber phoneNumber = PhoneNumberTestBuilder.aPhoneNumber().build();

        when(addressMapperMock.toDomain(addressDto)).thenReturn(address);
        when(emailMapperMock.toDomain(emailDto)).thenReturn(email);
        when(phoneNumberMapperMock.toDomain(phoneNumberDto)).thenReturn(phoneNumber);

        // when
        Customer customer = customerMapper.toDomain(new CustomerDto()
                .withFirstname("Tim")
                .withLastname("Timmelston")
                .withAddress(addressDto)
                .withEmail(emailDto)
                .withPhoneNumber(phoneNumberDto));

        // then
        Assertions.assertThat(customer)
                .isEqualToComparingFieldByField(Customer.CustomerBuilder.customer()
                        .withFirstname("Tim")
                        .withLastname("Timmelston")
                        .withAddress(address)
                        .withEmail(email)
                        .withPhoneNumber(phoneNumber)
                        .build());
        verify(addressMapperMock).toDomain(addressDto);
        verify(emailMapperMock).toDomain(emailDto);
        verify(phoneNumberMapperMock).toDomain(phoneNumberDto);

    }

    @Test
    void toDomain_mapTheID() {
        Customer customer = customerMapper.toDomain(new CustomerDto()
                .withId(UUID.randomUUID()));

        Assertions.assertThat(customer.getId()).isNotNull();
    }

}
