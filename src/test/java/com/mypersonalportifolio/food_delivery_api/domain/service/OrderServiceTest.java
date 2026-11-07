package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.OrderInputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.model.Product;
import com.mypersonalportifolio.food_delivery_api.domain.repository.OrderRepository;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.mapstruct.AddressMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CityService cityService;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private ProductService productService;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(orderRepository, cityService, addressMapper, productService);
    }

    @Test
    void shouldResolveProductsAndCalculateEachOrderItemTotal() {
        var productId = UUID.randomUUID();
        var product = new Product("Soup", "Daily soup", new BigDecimal("8.50"), true, null, null);
        var candidate = new OrderInputDTO(UUID.randomUUID(), UUID.randomUUID(), null,
                List.of(new OrderInputDTO.OrderItemInputDTO(productId, 3, "No onions")));
        when(productService.findValidProduct(productId)).thenReturn(product);

        var result = orderService.processItems(candidate);

        assertEquals(1, result.size());
        var item = result.iterator().next();
        assertEquals(product, item.getProduct());
        assertEquals(3, item.getQuantity());
        assertEquals(new BigDecimal("8.50"), item.getUnitPrice());
        assertEquals(new BigDecimal("25.50"), item.getTotalPrice());
        assertEquals("No onions", item.getNote());
        verify(productService).findValidProduct(productId);
    }
}
