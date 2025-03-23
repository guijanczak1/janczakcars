package br.com.dealership.janczakcars.port.input.order;

import br.com.dealership.janczakcars.adapter.input.order.dto.SaveOrderRequest;
import br.com.dealership.janczakcars.adapter.input.order.dto.SaveOrderResponse;
import br.com.dealership.janczakcars.adapter.input.order.dto.UpdateOrderStatusRequest;
import br.com.dealership.janczakcars.adapter.output.models.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Tag(name = "OrderController")
public interface OrderControllerPort {

    @Operation(summary = "Busca Ordem por ID", description = "Busca a Ordem por ID na base de pedidos")
    @ApiResponse(responseCode = "200", description = "Ok")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ApiResponse(responseCode = "400", description = "Erro de parametro")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor")
    ResponseEntity<Order> findOrderById(@PathVariable("id") UUID id);

    @Operation(summary = "Salvar Ordem", description = "Salvar ordem para multiplos produtos")
    @ApiResponse(responseCode = "200", description = "Ok")
    @ApiResponse(responseCode = "400", description = "Erro de payload")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor")
    ResponseEntity<SaveOrderResponse> saveOrder(@RequestBody SaveOrderRequest request);

    @Operation(summary = "Atualiza uma Ordem", description = "Atualizar uma ordem")
    @ApiResponse(responseCode = "200", description = "Ok")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ApiResponse(responseCode = "400", description = "Erro de payload")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor")
    ResponseEntity<SaveOrderResponse> updateStatus(@PathVariable UUID id, @RequestBody UpdateOrderStatusRequest updateStatus);
}
