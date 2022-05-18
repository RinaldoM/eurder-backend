package com.switchfully.order.api.customers.phonenumbers;

import com.switchfully.order.domain.customers.phonenumbers.PhoneNumber;
import com.switchfully.order.infrastructure.dto.Mapper;

import javax.inject.Named;

@Named
public class PhoneNumberMapper extends Mapper<PhoneNumberDto, PhoneNumber> {

    @Override
    public PhoneNumberDto toDto(PhoneNumber phoneNumber) {
        return new PhoneNumberDto()
                .withNumber(phoneNumber.getNumber())
                .withCountryCallingCode(phoneNumber.getCountryCallingCode());
    }

    @Override
    public PhoneNumber toDomain(PhoneNumberDto phoneNumberDto) {
        return PhoneNumber.PhoneNumberBuilder.phoneNumber()
                .withNumber(phoneNumberDto.getNumber())
                .withCountryCallingCode(phoneNumberDto.getCountryCallingCode())
                .build();
    }

}
