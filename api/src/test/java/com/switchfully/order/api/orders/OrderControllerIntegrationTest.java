package com.switchfully.order.api.orders;

import com.switchfully.order.ControllerIntegrationTest;
import com.switchfully.order.api.customers.CustomerController;
import com.switchfully.order.api.customers.CustomerDto;
import com.switchfully.order.api.customers.addresses.AddressDto;
import com.switchfully.order.api.customers.emails.EmailDto;
import com.switchfully.order.api.customers.phonenumbers.PhoneNumberDto;
import com.switchfully.order.api.items.ItemController;
import com.switchfully.order.api.items.ItemDto;
import com.switchfully.order.api.orders.dtos.ItemGroupDto;
import com.switchfully.order.api.orders.dtos.OrderAfterCreationDto;
import com.switchfully.order.api.orders.dtos.OrderCreationDto;
import com.switchfully.order.api.orders.dtos.OrderDto;
import com.switchfully.order.api.orders.dtos.reports.OrdersReportDto;
import com.switchfully.order.domain.customers.CustomerRepository;
import com.switchfully.order.domain.items.ItemRepository;
import com.switchfully.order.domain.items.prices.Price;
import com.switchfully.order.domain.orders.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.UUID;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

class OrderControllerIntegrationTest extends ControllerIntegrationTest {

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private ItemRepository itemRepository;

    @Inject
    private OrderRepository orderRepository;

    @Override
    public void clearDatabase() {
        orderRepository.getEntityManager().createNativeQuery("DELETE FROM ORDR_ORDER_ITEM").executeUpdate();
        orderRepository.getEntityManager().createQuery("DELETE FROM Order").executeUpdate();
        itemRepository.getEntityManager().createQuery("DELETE FROM Item").executeUpdate();
        customerRepository.getEntityManager().createQuery("DELETE FROM Customer").executeUpdate();
    }

    @Test
    void createOrder() {
        CustomerDto createdCustomer = doCallToCreateCustomer(createACustomer());
        ItemDto itemOne = doCallToCreateItem(createAnItem().withAmountOfStock(10).withPrice(10.0f));
        ItemDto itemTwo = doCallToCreateItem(createAnItem().withAmountOfStock(7).withPrice(2.5f));

        OrderCreationDto orderDto = new OrderCreationDto()
                .withCustomerId(createdCustomer.getId())
                .withItemGroups(
                        new ItemGroupDto()
                                .withItemId(itemOne.getId())
                                .withOrderedAmount(8),
                        new ItemGroupDto()
                                .withItemId(itemTwo.getId())
                                .withOrderedAmount(5)
                );

        OrderAfterCreationDto orderAfterCreationDto = new TestRestTemplate()
                .postForObject(format("http://localhost:%s/%s", getPort(), OrderController.RESOURCE_NAME), orderDto,
                        OrderAfterCreationDto.class);

        assertThat(orderAfterCreationDto).isNotNull();
        assertThat(orderAfterCreationDto.getOrderId()).isNotNull().isNotEmpty();
        assertThat(orderAfterCreationDto.getTotalPrice()).isEqualTo(92.5f);
    }

    @Test
    void getAllOrders_includeOnlyShippableToday() {
        CustomerDto existingCustomer1 = doCallToCreateCustomer(createACustomer());
        ItemDto existingItem1 = doCallToCreateItem(createAnItem());
        ItemDto existingItem2 = doCallToCreateItem(createAnItem());

        new TestRestTemplate()
                .postForObject(format("http://localhost:%s/%s", getPort(), OrderController.RESOURCE_NAME), new OrderCreationDto()
                                .withItemGroups(
                                        new ItemGroupDto().withItemId(existingItem1.getId()).withOrderedAmount(8),
                                        new ItemGroupDto().withItemId(existingItem2.getId()).withOrderedAmount(8))
                                .withCustomerId(existingCustomer1.getId()),
                        OrderAfterCreationDto.class);
        new TestRestTemplate()
                .postForObject(format("http://localhost:%s/%s", getPort(), OrderController.RESOURCE_NAME), new OrderCreationDto()
                                .withItemGroups(
                                        new ItemGroupDto().withItemId(existingItem2.getId()).withOrderedAmount(4))
                                .withCustomerId(existingCustomer1.getId()),
                        OrderAfterCreationDto.class);

        OrderDto[] orders = new TestRestTemplate()
                .getForObject(format("http://localhost:%s/%s?shippableToday=true", getPort(),
                        OrderController.RESOURCE_NAME), OrderDto[].class);

        assertThat(orders).hasSize(2);
        assertThat(orders[0].getItemGroups()).isEmpty();
        assertThat(orders[1].getItemGroups()).isEmpty();
    }

    @Test
    void reorderOrder() {
        CustomerDto existingCustomer1 = doCallToCreateCustomer(createACustomer());
        ItemDto existingItem1 = doCallToCreateItem(createAnItem().withAmountOfStock(12).withPrice(10.0f));

        OrderAfterCreationDto createdOrder = new TestRestTemplate()
                .postForObject(format("http://localhost:%s/%s", getPort(), OrderController.RESOURCE_NAME),
                        new OrderCreationDto()
                                .withCustomerId(existingCustomer1.getId())
                                .withItemGroups(
                                        new ItemGroupDto().withOrderedAmount(6)
                                                .withItemId(existingItem1.getId())
                                ),
                        OrderAfterCreationDto.class);

        OrderAfterCreationDto reorderedOrderDto = new TestRestTemplate()
                .postForObject(format("http://localhost:%s/%s/%s/%s", getPort(), OrderController.RESOURCE_NAME,
                        createdOrder.getOrderId(), "reorder"), null, OrderAfterCreationDto.class);

        assertThat(reorderedOrderDto).isNotNull();
        assertThat(reorderedOrderDto.getOrderId()).isNotNull().isNotEmpty().isNotEqualTo(createdOrder.getOrderId());
        assertThat(reorderedOrderDto.getTotalPrice()).isEqualTo(60.0f);
        assertThat(orderRepository.get(UUID.fromString(reorderedOrderDto.getOrderId()))).isNotNull();

    }

    @Test
    void getOrdersForCustomerReport() {
        CustomerDto existingCustomer1 = doCallToCreateCustomer(createACustomer());
        ItemDto existingItem1 = doCallToCreateItem(createAnItem());
        ItemDto existingItem2 = doCallToCreateItem(createAnItem());

        OrderAfterCreationDto createdOrder1 = new TestRestTemplate()
                .postForObject(format("http://localhost:%s/%s", getPort(), OrderController.RESOURCE_NAME),
                        new OrderCreationDto()
                                .withCustomerId(existingCustomer1.getId())
                                .withItemGroups(
                                        new ItemGroupDto().withOrderedAmount(1).withItemId(existingItem1.getId()),
                                        new ItemGroupDto().withOrderedAmount(1).withItemId(existingItem2.getId())
                                ),
                        OrderAfterCreationDto.class);
        OrderAfterCreationDto createdOrder2 = new TestRestTemplate()
                .postForObject(format("http://localhost:%s/%s", getPort(), OrderController.RESOURCE_NAME),
                        new OrderCreationDto()
                                .withCustomerId(existingCustomer1.getId())
                                .withItemGroups(
                                        new ItemGroupDto().withOrderedAmount(1).withItemId(existingItem2.getId())
                                ),
                        OrderAfterCreationDto.class);

        OrdersReportDto ordersReportDto = new TestRestTemplate()
                .getForObject(format("http://localhost:%s/%s/%s/%s", getPort(), OrderController.RESOURCE_NAME,
                        "customers", existingCustomer1.getId()), OrdersReportDto.class);

        assertThat(ordersReportDto).isNotNull();
        assertThat(ordersReportDto.getTotalPriceOfAllOrders())
                .isEqualTo(Price.add(Price.create(new BigDecimal(createdOrder1.getTotalPrice())),
                        Price.create(new BigDecimal(createdOrder2.getTotalPrice()))).getAmount().floatValue());
        assertThat(ordersReportDto.getOrders()).hasSize(2);
        ordersReportDto.getOrders().forEach(order -> {
            assertThat(order.getOrderId()).isNotEmpty().isNotNull();
            assertThat(order.getItemGroups()).isNotEmpty();
        });
    }

    private CustomerDto doCallToCreateCustomer(CustomerDto requestBody) {
        return new TestRestTemplate()
                .postForObject(format("http://localhost:%s/%s", getPort(), CustomerController.RESOURCE_NAME), requestBody, CustomerDto.class);
    }

    private ItemDto doCallToCreateItem(ItemDto requestBody) {
        return new TestRestTemplate()
                .postForObject(format("http://localhost:%s/%s", getPort(), ItemController.RESOURCE_NAME),
                        requestBody, ItemDto.class);
    }

    private CustomerDto createACustomer() {
        return new CustomerDto()
                .withFirstname("Bruce")
                .withLastname("Wayne")
                .withEmail(new EmailDto()
                        .withLocalPart("brucy")
                        .withDomain("bat.net")
                        .withComplete("brucy@bat.net"))
                .withPhoneNumber(new PhoneNumberDto()
                        .withNumber("485212121")
                        .withCountryCallingCode("+32"))
                .withAddress(new AddressDto()
                        .withStreetName("Secretstreet")
                        .withHouseNumber("841")
                        .withPostalCode("1238")
                        .withCountry("GothamCountry"));
    }

    private ItemDto createAnItem() {
        return new ItemDto()
                .withName("Half-Life 3")
                .withDescription("Boehoehoe...")
                .withPrice(45.50f)
                .withAmountOfStock(50510);
    }

}
