package com.switchfully.order.domain.orders;

import com.switchfully.order.IntegrationTest;
import com.switchfully.order.domain.customers.Customer;
import com.switchfully.order.domain.customers.CustomerRepository;
import com.switchfully.order.domain.items.Item;
import com.switchfully.order.domain.items.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

import static com.switchfully.order.domain.customers.CustomerTestBuilder.aCustomer;
import static com.switchfully.order.domain.items.ItemTestBuilder.anItem;
import static com.switchfully.order.domain.orders.OrderTestBuilder.anOrder;
import static com.switchfully.order.domain.orders.orderitems.OrderItemTestBuilder.anOrderItem;

class OrderRepositoryIntegrationTest extends IntegrationTest {

    @Inject
    private OrderRepository repository;

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private ItemRepository itemRepository;

    private Customer customer;
    private Item item;

    @BeforeEach
    void createEntities() {
        customer = customerRepository.save(aCustomer().build());
        item = itemRepository.save(anItem().build());
    }

    @Test
    void save() {
        Order orderToSave = anOrder()
                .withCustomerId(customer.getId())
                .withOrderItems(anOrderItem().withItemId(item.getId()).build())
                .build();

        Order savedOrder = repository.save(orderToSave);

        Assertions.assertThat(savedOrder.getId()).isNotNull();
        Assertions.assertThat(repository.get(savedOrder.getId()))
                .usingRecursiveComparison()
                .isEqualTo(savedOrder);
    }

    @Test
    void get() {
        Order savedOrder = repository.save(anOrder()
                .withCustomerId(customer.getId())
                .withOrderItems(anOrderItem().withItemId(item.getId()).build())
                .build());

        Order actualOrder = repository.get(savedOrder.getId());

        Assertions.assertThat(actualOrder)
                .usingRecursiveComparison()
                .isEqualTo(savedOrder);
    }

    @Test
    void getAll() {
        Order orderOne = repository.save(anOrder()
                .withCustomerId(customer.getId())
                .withOrderItems(anOrderItem().withItemId(item.getId()).build())
                .build());
        Order orderTwo = repository.save(anOrder()
                .withCustomerId(customer.getId())
                .withOrderItems(anOrderItem().withItemId(item.getId()).build())
                .build());

        List<Order> allOrders = repository.getAll();

        Assertions.assertThat(allOrders)
                .containsExactlyInAnyOrder(orderOne, orderTwo);
    }
}
