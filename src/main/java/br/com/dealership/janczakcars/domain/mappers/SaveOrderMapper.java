package br.com.dealership.janczakcars.domain.mappers;

import br.com.dealership.janczakcars.adapter.input.order.dto.SaveProductOrderRequest;
import br.com.dealership.janczakcars.adapter.input.order.dto.SaveOrderRequest;
import br.com.dealership.janczakcars.adapter.output.models.Order;
import br.com.dealership.janczakcars.adapter.output.models.OrderProduct;
import br.com.dealership.janczakcars.adapter.output.models.Product;
import br.com.dealership.janczakcars.domain.enums.OrderStatusEnum;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class SaveOrderMapper {

    public Order mapperOrderToEntity(SaveOrderRequest dto) {
        return new Order(null,
                dto.order_description,
                OrderStatusEnum.CRIADO.getDescription(),
                dto.total_price,
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    public Product mapperProductToEntity(SaveProductOrderRequest dto, Product entity) {
        return new Product(dto.getId_product(),
                entity.getName(),
                entity.getPrice(),
                entity.getAmount(),
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    public List<OrderProduct> mapperOrderProductToEntity(Order order, List<Product> products) {
        List<OrderProduct> list = new ArrayList<>();

        products.stream().forEach(product -> {
            list.add(new OrderProduct(
                    UUID.randomUUID(),
                    order,
                    product,
                    product.getPrice(),
                    LocalDateTime.now(),
                    LocalDateTime.now()
            ));
        });

        return list;
    }
}
