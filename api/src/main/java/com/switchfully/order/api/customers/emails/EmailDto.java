package com.switchfully.order.api.customers.emails;

public class EmailDto {

    private String localPart;
    private String domain;
    private String complete;

    public EmailDto() {
    }

    public EmailDto withLocalPart(String localPart) {
        this.localPart = localPart;
        return this;
    }

    public EmailDto withDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public EmailDto withComplete(String complete) {
        this.complete = complete;
        return this;
    }

    public String getLocalPart() {
        return localPart;
    }

    public String getDomain() {
        return domain;
    }

    public String getComplete() {
        return complete;
    }
}
