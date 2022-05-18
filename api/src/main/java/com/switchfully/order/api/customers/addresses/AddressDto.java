package com.switchfully.order.api.customers.addresses;

public class AddressDto {

    private String streetName;
    private String houseNumber;
    private String postalCode;
    private String country;

    public AddressDto() {
    }

    public AddressDto withStreetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public AddressDto withHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public AddressDto withPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public AddressDto withCountry(String country) {
        this.country = country;
        return this;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }
}

