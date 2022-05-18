package com.switchfully.order.api.customers.emails;

import com.switchfully.order.domain.customers.emails.Email;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.switchfully.order.domain.customers.emails.Email.EmailBuilder.email;

class EmailMapperTest {

    @Test
    void toDto() {
        EmailDto emailDto = new EmailMapper().toDto(email()
                .withLocalPart("mail")
                .withDomain("domain.be")
                .withComplete("mail@domain.be").build());

        Assertions.assertThat(emailDto)
                .isEqualToComparingFieldByField(new EmailDto()
                        .withLocalPart("mail")
                        .withDomain("domain.be")
                        .withComplete("mail@domain.be"));
    }

    @Test
    void toDomain() {
        Email email = new EmailMapper().toDomain(new EmailDto()
                .withLocalPart("mail")
                .withDomain("domain.be")
                .withComplete("mail@domain.be"));

        Assertions.assertThat(email)
                .isEqualToComparingFieldByField(email()
                        .withLocalPart("mail")
                        .withDomain("domain.be")
                        .withComplete("mail@domain.be")
                        .build());
    }

}
