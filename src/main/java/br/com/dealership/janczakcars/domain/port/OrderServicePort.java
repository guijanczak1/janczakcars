package br.com.dealership.janczakcars.domain.port;

import br.com.dealership.janczakcars.adapter.input.order.dto.SaveOrderRequest;
import br.com.dealership.janczakcars.adapter.input.order.dto.SaveOrderResponse;
import br.com.dealership.janczakcars.adapter.output.models.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderServicePort {

    public Optional<Order> getOrderById(UUID id);

    public SaveOrderResponse saveOrder(SaveOrderRequest request);

    public SaveOrderResponse updateStatus(Order order, int status);
}
