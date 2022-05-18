package com.switchfully.order.api.customers.phonenumbers;

public class PhoneNumberDto {

    private String number;
    private String countryCallingCode;

    public PhoneNumberDto() {
    }

    public PhoneNumberDto withNumber(String number) {
        this.number = number;
        return this;
    }

    public PhoneNumberDto withCountryCallingCode(String countryCallingCode) {
        this.countryCallingCode = countryCallingCode;
        return this;
    }

    public String getNumber() {
        return number;
    }

    public String getCountryCallingCode() {
        return countryCallingCode;
    }
}
