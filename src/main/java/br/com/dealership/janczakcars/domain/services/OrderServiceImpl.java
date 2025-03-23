package br.com.dealership.janczakcars.domain.services;

import br.com.dealership.janczakcars.adapter.input.order.dto.SaveOrderRequest;
import br.com.dealership.janczakcars.adapter.input.order.dto.SaveOrderResponse;
import br.com.dealership.janczakcars.adapter.output.models.Order;
import br.com.dealership.janczakcars.adapter.output.models.OrderProduct;
import br.com.dealership.janczakcars.adapter.output.models.Product;
import br.com.dealership.janczakcars.domain.enums.OrderStatusEnum;
import br.com.dealership.janczakcars.domain.mappers.SaveOrderMapper;
import br.com.dealership.janczakcars.domain.port.OrderServicePort;
import br.com.dealership.janczakcars.port.output.OrderProductRepository;
import br.com.dealership.janczakcars.port.output.OrderRepository;
import br.com.dealership.janczakcars.port.output.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderServicePort {

    private final OrderRepository orderRepository;

    private final OrderProductRepository orderProductRepository;

    private final ProductRepository productRepository;

    private final SaveOrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderProductRepository orderProductRepository,
                            ProductRepository productRepository,
                            SaveOrderMapper orderMapper)  {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;

    }

    public Optional<Order> getOrderById (UUID id) {
        return orderRepository.findById(id);
    }

    @Override
    @Transactional
    public SaveOrderResponse saveOrder(SaveOrderRequest request) {

        List<Product> products = new ArrayList<>();

        request.products.stream().forEach(productDto -> {

            Product productEntity = productRepository.findById(productDto.getId_product()).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            Product product = orderMapper.mapperProductToEntity(productDto, productEntity);

            products.add(product);

        });

        request.total_price = products.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = orderMapper.mapperOrderToEntity(request);

        orderRepository.save(order);

        List<OrderProduct> orderProducts = orderMapper.mapperOrderProductToEntity(order, products);

        orderProductRepository.saveAllAndFlush(orderProducts);

        return new SaveOrderResponse(order.getId_order());
    }

    @Override
    @Transactional
    public SaveOrderResponse updateStatus(Order order, int status) {
        if(OrderStatusEnum.isValidCode(status)) {
            order.setStatus(OrderStatusEnum.getDescriptionByCode(status));
            order.setUpdate_at(LocalDateTime.now());
            orderRepository.saveAndFlush(order);
        }

        return new SaveOrderResponse(order.getId_order());
    }

}
