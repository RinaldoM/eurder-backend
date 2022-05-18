package com.switchfully.order.service.items;

import com.switchfully.order.domain.orders.orderitems.events.OrderItemCreatedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;


public class ItemEventHandler {

    @Named
    public static class OrderItemCreatedEventListener implements ApplicationListener<OrderItemCreatedEvent> {
        private ItemService itemService;

        @Inject
        public OrderItemCreatedEventListener(ItemService itemService) {
            this.itemService = itemService;
        }

        @Override
        @Transactional(propagation = Propagation.MANDATORY)
        public void onApplicationEvent(OrderItemCreatedEvent event) {
            itemService.decrementStockForItem(
                    event.getOrderItem().getItemId(),
                    event.getOrderItem().getOrderedAmount());
        }
    }

    /*
     * Other Listeners, for other events, but all to the interest of Item,
     * can be added as static nested classes as well.
     */

}
