package br.com.dealership.janczakcars.adapter.input.order.dto;

import br.com.dealership.janczakcars.adapter.input.product.dto.ProductRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SaveOrderRequest {

    public String order_description;

    @JsonIgnore
    public BigDecimal total_price;

    @NotNull
    public List<SaveProductOrderRequest> products;

}
