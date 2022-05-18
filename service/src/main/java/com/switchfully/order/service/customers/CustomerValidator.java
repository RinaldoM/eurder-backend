package com.switchfully.order.service.customers;

import com.switchfully.order.domain.EntityValidator;
import com.switchfully.order.domain.customers.Customer;

import javax.inject.Named;

/**
 * Instead of our custom validator(s),
 * we could also use Javax Validation (we didn't use it here, to not over-complexify the solution).
 * That way, we can indicate on our Domain classes (such as Customer and Item)
 * what fields are required and what the valid values are for each field.
 */
@Named
public class CustomerValidator extends EntityValidator<Customer> {

    @Override
    protected boolean isAFieldEmptyOrNull(Customer customer) {
        return isNull(customer)
                || isEmptyOrNull(customer.getFirstname())
                || isEmptyOrNull(customer.getLastname())
                || isNull(customer.getAddress())
                    || isEmptyOrNull(customer.getAddress().getStreetName())
                    || isEmptyOrNull(customer.getAddress().getHouseNumber())
                    || isEmptyOrNull(customer.getAddress().getPostalCode())
                    || isEmptyOrNull(customer.getAddress().getCountry())
                || isNull(customer.getEmail())
                    || isEmptyOrNull(customer.getEmail().getDomain())
                    || isEmptyOrNull(customer.getEmail().getLocalPart())
                || isNull(customer.getPhoneNumber())
                    || isEmptyOrNull(customer.getPhoneNumber().getCountryCallingCode())
                    || isEmptyOrNull(customer.getPhoneNumber().getNumber());
    }

}
