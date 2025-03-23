package br.com.dealership.janczakcars.adapter.input.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveProductResponse {

    public UUID id_product;
}
