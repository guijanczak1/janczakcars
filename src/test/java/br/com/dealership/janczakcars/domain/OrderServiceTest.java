package br.com.dealership.janczakcars.domain;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.dealership.janczakcars.adapter.input.order.dto.SaveOrderRequest;
import br.com.dealership.janczakcars.adapter.input.order.dto.SaveOrderResponse;
import br.com.dealership.janczakcars.adapter.input.order.dto.SaveProductOrderRequest;
import br.com.dealership.janczakcars.adapter.output.models.Order;
import br.com.dealership.janczakcars.adapter.output.models.OrderProduct;
import br.com.dealership.janczakcars.adapter.output.models.Product;
import br.com.dealership.janczakcars.domain.enums.OrderStatusEnum;
import br.com.dealership.janczakcars.domain.mappers.SaveOrderMapper;
import br.com.dealership.janczakcars.domain.services.OrderServiceImpl;
import br.com.dealership.janczakcars.port.output.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

class OrderServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderProductRepository orderProductRepository;

    @Mock
    private SaveOrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    private SaveOrderRequest request;

    private UUID orderId;

    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        request = new SaveOrderRequest();
        request.setProducts(Arrays.asList(
                new SaveProductOrderRequest(UUID.fromString("61356b0d-002b-4317-9b33-0ad08b606202")),
                new SaveProductOrderRequest(UUID.fromString("61356b0d-002b-4317-9b33-0ad08b606203"))
        ));

        orderId = UUID.randomUUID();
        order = new Order();
        order.setId_order(orderId);
        order.setStatus("Criado");
        order.setTotal_price(BigDecimal.valueOf(250.0));
        order.setUpdate_at(LocalDateTime.now());
    }

    @Test
    void testSaveOrder() {
        Product product1 = new Product(UUID.fromString("61356b0d-002b-4317-9b33-0ad08b606202"), "Product 1", BigDecimal.valueOf(100), 1, LocalDateTime.now(), LocalDateTime.now());
        Product product2 = new Product(UUID.fromString("61356b0d-002b-4317-9b33-0ad08b606203"), "Product 2", BigDecimal.valueOf(200), 2, LocalDateTime.now(), LocalDateTime.now());
        when(productRepository.findById(UUID.fromString("61356b0d-002b-4317-9b33-0ad08b606202"))).thenReturn(Optional.of(product1));
        when(productRepository.findById(UUID.fromString("61356b0d-002b-4317-9b33-0ad08b606203"))).thenReturn(Optional.of(product2));

        when(orderMapper.mapperProductToEntity(any(), eq(product1))).thenReturn(product1);
        when(orderMapper.mapperProductToEntity(any(), eq(product2))).thenReturn(product2);

        Order orderMock = new Order();
        orderMock.setId_order(UUID.fromString("61356b0d-002b-4317-9b33-0ad08b606202"));
        when(orderMapper.mapperOrderToEntity(any(SaveOrderRequest.class))).thenReturn(orderMock);

        when(orderRepository.save(any(Order.class))).thenReturn(orderMock);

        OrderProduct orderProduct1 = new OrderProduct();
        OrderProduct orderProduct2 = new OrderProduct();
        when(orderMapper.mapperOrderProductToEntity(eq(orderMock), anyList())).thenReturn(Arrays.asList(orderProduct1, orderProduct2));

        when(orderProductRepository.saveAllAndFlush(anyList())).thenReturn(Arrays.asList(orderProduct1, orderProduct2));

        SaveOrderResponse response = orderService.saveOrder(request);

        assertNotNull(response);
        assertEquals(UUID.fromString("61356b0d-002b-4317-9b33-0ad08b606202"), response.getId_order());
        verify(productRepository, times(2)).findById(any(UUID.class));
        verify(orderRepository).save(any(Order.class));
        verify(orderProductRepository).saveAllAndFlush(anyList());

        assertEquals(BigDecimal.valueOf(300), request.total_price);
    }

    @Test
    void testProdutoNaoEncontrado() {
        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();

        when(productRepository.findById(productId1)).thenReturn(Optional.empty());
        when(productRepository.findById(productId2)).thenReturn(Optional.of(new Product()));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            orderService.saveOrder(request);
        });

        assertEquals("Produto não encontrado", thrown.getMessage());
    }

    @Test
    void testGetOrderByIdSuccess() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.getOrderById(orderId);

        assertTrue(result.isPresent());
        assertEquals(orderId, result.get().getId_order());
        assertEquals(BigDecimal.valueOf(250.0), result.get().getTotal_price());
    }

    @Test
    void testGetOrderByIdNotFound() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        Optional<Order> result = orderService.getOrderById(orderId);

        assertFalse(result.isPresent());
    }

    @Test
    void testUpdateStatusSuccess() {
        int validStatus = 1;

        try (MockedStatic<OrderStatusEnum> mockedEnum = mockStatic(OrderStatusEnum.class)) {
            mockedEnum.when(() -> OrderStatusEnum.isValidCode(validStatus)).thenReturn(true);

            mockedEnum.when(() -> OrderStatusEnum.getDescriptionByCode(validStatus)).thenReturn("Criado");

            when(orderRepository.saveAndFlush(order)).thenReturn(order);

            SaveOrderResponse response = orderService.updateStatus(order, validStatus);

            assertNotNull(response);
            assertEquals(orderId, response.getId_order());
            assertEquals("Criado", order.getStatus());
            verify(orderRepository).saveAndFlush(order);
        }
    }

    @Test
    void testUpdateStatusInvalid() {
        int invalidStatus = 999;

        try (MockedStatic<OrderStatusEnum> mockedEnum = mockStatic(OrderStatusEnum.class)) {
            mockedEnum.when(() -> OrderStatusEnum.isValidCode(invalidStatus)).thenReturn(false);

            SaveOrderResponse response = orderService.updateStatus(order, invalidStatus);

            assertNotNull(response);
            assertEquals(orderId, response.getId_order());
            assertEquals("Criado", order.getStatus());
            verify(orderRepository, times(0)).saveAndFlush(order);
        }
    }
}