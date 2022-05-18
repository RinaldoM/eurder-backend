package com.switchfully.order.api.customers;

import java.util.UUID;

public class CustomerOverviewDto {

    private String id;
    private String firstname;
    private String lastname;

    public CustomerOverviewDto() {
    }

    public CustomerOverviewDto withId(UUID id) {
        this.id = id.toString();
        return this;
    }

    public CustomerOverviewDto withFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public CustomerOverviewDto withLastname(String lastname) {
        this.lastname = lastname;
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

}
