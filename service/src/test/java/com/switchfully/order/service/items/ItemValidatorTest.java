package com.switchfully.order.service.items;

import com.switchfully.order.domain.items.Item;
import com.switchfully.order.domain.items.prices.Price;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static com.switchfully.order.domain.items.ItemTestBuilder.anItem;
import static org.assertj.core.api.Assertions.assertThat;

class ItemValidatorTest {

    @Test
    void isValidForCreation_happyPath() {
        assertThat(new ItemValidator()
                .isValidForCreation(anItem()
                        .build()))
                .isTrue();
    }

    @Test
    void isValidForCreation_givenId_thenNotValidForCreation() {
        assertThat(new ItemValidator()
                .isValidForCreation(anItem()
                        .withId(UUID.randomUUID())
                        .build()))
                .isFalse();
    }

    @Test
    void isValidForCreation_givenEmptyName_thenNotValidForCreation() {
        assertThat(new ItemValidator()
            .isValidForCreation(anItem()
                    .withName("")
                    .build()))
                .isFalse();
    }

    @Test
    void isValidForCreation_givenNullName_thenNotValidForCreation() {
        assertThat(new ItemValidator()
            .isValidForCreation(anItem()
                    .withName(null)
                    .build()))
                .isFalse();
    }

    @Test
    void isValidForCreation_givenEmptyDescription_thenNotValidForCreation() {
        assertThat(new ItemValidator()
            .isValidForCreation(anItem()
                    .withDescription("")
                    .build()))
                .isFalse();
    }

    @Test
    void isValidForCreation_givenNullDescription_thenNotValidForCreation() {
        assertThat(new ItemValidator()
            .isValidForCreation(anItem()
                    .withDescription(null)
                    .build()))
                .isFalse();
    }

    @Test
    void isValidForCreation_givenZeroPrice_thenNotValidForCreation() {
        assertThat(new ItemValidator()
            .isValidForCreation(anItem()
                    .withPrice(Price.create(BigDecimal.valueOf(0)))
                    .build()))
                .isFalse();
    }

    @Test
    void isValidForCreation_givenNegativePrice_thenNotValidForCreation() {
        assertThat(new ItemValidator()
            .isValidForCreation(anItem()
                    .withPrice(Price.create(BigDecimal.valueOf(-1)))
                    .build()))
                .isFalse();
    }

    @Test
    void isValidForCreation_givenNullPrice_thenNotValidForCreation() {
        assertThat(new ItemValidator()
            .isValidForCreation(anItem()
                    .withPrice(null)
                    .build()))
                .isFalse();
    }

    @Test
    void isValidForCreation_givenNegativeStock_thenNotValidForCreation() {
        assertThat(new ItemValidator()
            .isValidForCreation(anItem()
                    .withAmountOfStock(-1)
                    .build()))
                .isFalse();
    }

    @Test
    void isValidForUpdating_happyPath() {
        Item item = anItem().withId(UUID.randomUUID()).build();

        assertThat(new ItemValidator()
                .isValidForUpdating(item))
                .isTrue();
    }

    @Test
    void isValidForUpdating_givenNoId_thenNotValidForUpdating() {
        assertThat(new ItemValidator()
                .isValidForUpdating(anItem().build()))
                .isFalse();
    }

}
