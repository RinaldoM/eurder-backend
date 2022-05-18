package com.switchfully.order.api.customers;

import com.switchfully.order.api.customers.addresses.AddressDto;
import com.switchfully.order.api.customers.emails.EmailDto;
import com.switchfully.order.api.customers.phonenumbers.PhoneNumberDto;

import java.util.UUID;

public class CustomerDto {

    private String id;
    private String firstname;
    private String lastname;
    private EmailDto email;
    private AddressDto address;
    private PhoneNumberDto phoneNumber;

    public CustomerDto() {
    }

    public CustomerDto withId(UUID id) {
        this.id = id.toString();
        return this;
    }

    public CustomerDto withFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public CustomerDto withLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public CustomerDto withEmail(EmailDto email) {
        this.email = email;
        return this;
    }

    public CustomerDto withAddress(AddressDto address) {
        this.address = address;
        return this;
    }

    public CustomerDto withPhoneNumber(PhoneNumberDto phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public EmailDto getEmail() {
        return email;
    }

    public AddressDto getAddress() {
        return address;
    }

    public PhoneNumberDto getPhoneNumber() {
        return phoneNumber;
    }
}
