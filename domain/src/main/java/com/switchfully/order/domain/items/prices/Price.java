package com.switchfully.order.domain.items.prices;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public final class Price {

    @Access(AccessType.FIELD)
    private BigDecimal amount;

    /** JPA requires a no-arg constructor */
    private Price() {
        amount = null;
    }

    private Price(BigDecimal amount) {
        this.amount = amount;
    }

    public static Price create(BigDecimal amount) {
        return new Price(amount);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public static Price add(Price price1, Price price2) {
        return Price.create(price1.getAmount().add(price2.getAmount()));
    }

    public boolean sameAs(Price otherPrice) {
        return amount.equals(otherPrice.getAmount());
    }

    @Override
    public String toString() {
        return "Price{" + "amount=" + amount + '}';
    }
}
