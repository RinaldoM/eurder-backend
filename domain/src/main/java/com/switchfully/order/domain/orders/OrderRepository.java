package com.switchfully.order.domain.orders;

import com.switchfully.order.domain.Repository;
import com.switchfully.order.domain.orders.orderitems.events.OrderItemCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.UUID;

@Named
public class OrderRepository extends Repository<Order> {

    private final ApplicationEventPublisher eventPublisher;

    @Inject
    public OrderRepository(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Order save(Order entity) {
        Order savedOrder = super.save(entity);
        savedOrder.getOrderItems()
                .forEach(orderItem -> eventPublisher.publishEvent(new OrderItemCreatedEvent(orderItem)));
        return savedOrder;
    }

    @Override
    public List<Order> getAll() {
        return getEntityManager()
                .createQuery("FROM Order", Order.class)
                .getResultList();
    }

    @Override
    public Order get(UUID entityId) {
        return getEntityManager()
                .createQuery("FROM Order where id = :id", Order.class)
                    .setParameter("id", entityId.toString())
                .getSingleResult();
    }

    public List<Order> getOrdersForCustomer(UUID customerId) {
        return getEntityManager()
                .createQuery("FROM Order where customerId = :id", Order.class)
                    .setParameter("id", customerId.toString())
                .getResultList();
    }
}
