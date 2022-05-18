package com.switchfully.order.service.items;

import com.switchfully.order.domain.EntityValidator;
import com.switchfully.order.domain.items.Item;

import javax.inject.Named;

/**
 * Instead of our custom validator(s),
 * we could also use Javax Validation (we didn't use it here, to not over-complexify the solution).
 * That way, we can indicate on our Domain classes (such as Customer and Item)
 * what fields are required and what the valid values are for each field.
 */
@Named
public class ItemValidator extends EntityValidator<Item>{

    @Override
    protected boolean isAFieldEmptyOrNull(Item item) {
        return isNull(item)
                || isEmptyOrNull(item.getName())
                || isEmptyOrNull(item.getDescription())
                || item.getAmountOfStock() < 0
                || isNull(item.getPrice())
                    || item.getPrice().getAmount().floatValue() <= 0;
    }
}
