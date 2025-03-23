package br.com.dealership.janczakcars.adapter.input.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SaveProductOrderRequest {
    public UUID id_product;
}
