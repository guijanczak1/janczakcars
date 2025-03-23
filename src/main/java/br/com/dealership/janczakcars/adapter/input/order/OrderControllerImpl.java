package br.com.dealership.janczakcars.adapter.input.order;

import br.com.dealership.janczakcars.adapter.input.order.dto.SaveOrderRequest;
import br.com.dealership.janczakcars.adapter.input.order.dto.SaveOrderResponse;
import br.com.dealership.janczakcars.adapter.input.order.dto.UpdateOrderStatusRequest;
import br.com.dealership.janczakcars.adapter.output.models.Order;
import br.com.dealership.janczakcars.domain.port.OrderServicePort;
import br.com.dealership.janczakcars.port.input.order.OrderControllerPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/order")
public class OrderControllerImpl implements OrderControllerPort {

    private final OrderServicePort orderService;

    @Autowired
    public OrderControllerImpl(OrderServicePort orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Order> findOrderById(@PathVariable UUID id) {
        return orderService.getOrderById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    @Override
    public ResponseEntity<SaveOrderResponse> saveOrder(@RequestBody SaveOrderRequest request) {
        return ResponseEntity.ok(orderService.saveOrder(request));
    }

    @PatchMapping("/{id}/status")
    @Override
    public ResponseEntity<SaveOrderResponse> updateStatus(@PathVariable UUID id, @RequestBody UpdateOrderStatusRequest updateStatus) {
        Optional<Order> order = orderService.getOrderById(id);

        if(order.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(orderService.updateStatus(order.stream().findAny().orElseThrow(), updateStatus.getStatus()));

    }

}
