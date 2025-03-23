package br.com.dealership.janczakcars.adapter.input.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveOrderResponse {

    public UUID id_order;
}
