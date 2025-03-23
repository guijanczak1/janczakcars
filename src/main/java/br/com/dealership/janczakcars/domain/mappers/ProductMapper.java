package br.com.dealership.janczakcars.domain.mappers;

import br.com.dealership.janczakcars.adapter.input.product.dto.ProductRequest;
import br.com.dealership.janczakcars.adapter.output.models.Product;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ProductMapper {
    public Product mapperProductToEntity(ProductRequest dto) {
        return new Product(UUID.randomUUID(),
                dto.getName(),
                dto.getPrice(),
                dto.getAmount(),
                LocalDateTime.now(),
                LocalDateTime.now());
    }
}
