package br.com.dealership.janczakcars.adapter.input.product.dto;

import jakarta.validation.Valid;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    public UUID id_product;

    @Valid
    public String name;

    public BigDecimal price;

    public int amount;

}
