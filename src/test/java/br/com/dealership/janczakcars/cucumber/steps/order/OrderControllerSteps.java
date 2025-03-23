package br.com.dealership.janczakcars.cucumber.steps.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import br.com.dealership.janczakcars.adapter.input.order.OrderControllerImpl;
import br.com.dealership.janczakcars.adapter.input.order.dto.SaveOrderRequest;
import br.com.dealership.janczakcars.adapter.input.order.dto.SaveOrderResponse;
import br.com.dealership.janczakcars.adapter.input.order.dto.UpdateOrderStatusRequest;
import br.com.dealership.janczakcars.adapter.output.models.Order;
import br.com.dealership.janczakcars.domain.port.OrderServicePort;
import io.cucumber.java.en.*;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

public class OrderControllerSteps {

    private OrderServicePort orderService;
    private OrderControllerImpl orderController;
    private ResponseEntity<?> response;
    private UUID orderId;

    @Given("um pedido com ID {string} existe no sistema")
    public void um_pedido_com_ID_existe_no_sistema(String id) {
        orderId = UUID.fromString(id);
        Order order = new Order();
        order.setId_order(orderId);
        order.setStatus("CRIADO");

        orderService = mock(OrderServicePort.class);
        when(orderService.getOrderById(orderId)).thenReturn(Optional.of(order));

        orderController = new OrderControllerImpl(orderService);
    }

    @When("faço uma requisição GET para {string}")
    public void facoUmaRequisicaoGetParaPedido(String endpoint) {
        response = orderController.findOrderById(orderId);
    }

    @Then("a resposta deve ter status {int}")
    public void a_resposta_deve_ter_status(Integer statusCode) {
        assertEquals(statusCode, response.getStatusCodeValue());
    }

    @And("deve conter os detalhes do pedido")
    public void deve_conter_os_detalhes_do_pedido() {
        assertEquals(orderId, ((Order) response.getBody()).getId_order());
    }

    @Given("um novo pedido válido")
    public void um_novo_pedido_valido() {
        orderService = mock(OrderServicePort.class);
        orderController = new OrderControllerImpl(orderService);

        SaveOrderRequest request = new SaveOrderRequest();
        SaveOrderResponse responseMock = new SaveOrderResponse(UUID.randomUUID());

        when(orderService.saveOrder(any())).thenReturn(responseMock);
    }

    @When("faço uma requisição POST para {string}")
    public void faco_uma_requisicao_post_para(String endpoint) {
        response = orderController.saveOrder(new SaveOrderRequest());
    }

    @And("deve conter um ID de pedido válido")
    public void deve_conter_um_id_de_pedido_valido() {
        assertEquals(UUID.class, ((SaveOrderResponse) response.getBody()).getId_order().getClass());
    }

    @Given("o novo status é {string}")
    public void o_novo_status_e(String status) {
        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest();
        request.setStatus(Integer.parseInt(status));
    }

    @When("faço uma requisição PATCH para {string}")
    public void faco_uma_requisicao_patch_para(String endpoint) {
        response = orderController.updateStatus(orderId, new UpdateOrderStatusRequest(2));
    }
}
