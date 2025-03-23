package br.com.dealership.janczakcars.cucumber.steps.product;

import br.com.dealership.janczakcars.adapter.input.product.ProductControllerImpl;
import br.com.dealership.janczakcars.adapter.input.product.dto.ProductRequest;
import br.com.dealership.janczakcars.adapter.input.product.dto.SaveProductResponse;
import br.com.dealership.janczakcars.adapter.output.models.Product;
import br.com.dealership.janczakcars.domain.port.ProductServicePort;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductControllerSteps {

    private ProductServicePort productService;
    private ProductControllerImpl productController;
    private ResponseEntity<?> response;
    private UUID productId;

    @Before
    public void setUp() {
        productService = mock(ProductServicePort.class);
        productController = new ProductControllerImpl(productService);
    }

    @Given("um produto com ID {string} existe no sistema")
    public void um_produto_com_id_existe_no_sistema(String id) {
        productId = UUID.fromString(id);
        Product product = new Product();
        product.setId_product(productId);
        product.setName("Produto Exemplo");
        product.setPrice(new BigDecimal("100.00"));
        product.setAmount(10);
        product.setCreated_at(LocalDateTime.now());
        product.setUpdate_at(LocalDateTime.now());

        productService = mock(ProductServicePort.class);
        when(productService.getProductById(productId)).thenReturn(Optional.of(product));

        productController = new ProductControllerImpl(productService);
    }

    @When("faço uma requisição GET para {string}")
    public void faco_uma_requisicao_get_para(String endpoint) {
        response = productController.findProductById(productId);
    }

    @Then("a resposta deve ter status {int}")
    public void a_resposta_deve_ter_status(int statusCode) {
        assertEquals(statusCode, response.getStatusCodeValue());
    }

    @And("deve conter os detalhes do produto")
    public void deve_conter_os_detalhes_do_produto() {
        assertEquals(productId, ((Product) response.getBody()).getId_product());
    }

    @Given("produtos estão cadastrados no sistema")
    public void produtos_estao_cadastrados_no_sistema() {
        Product product1 = new Product();
        product1.setId_product(UUID.randomUUID());
        product1.setName("Produto 1");
        product1.setPrice(new BigDecimal("100.00"));
        product1.setAmount(10);
        product1.setCreated_at(LocalDateTime.now());
        product1.setUpdate_at(LocalDateTime.now());

        Product product2 = new Product();
        product2.setId_product(UUID.randomUUID());
        product2.setName("Produto 2");
        product2.setPrice(new BigDecimal("150.00"));
        product2.setAmount(15);
        product2.setCreated_at(LocalDateTime.now());
        product2.setUpdate_at(LocalDateTime.now());

        List<Product> products = List.of(product1, product2);

        Page<Product> productPage = mock(Page.class);
        when(productService.listProducts(any(PageRequest.class))).thenReturn(productPage);
        when(productPage.getContent()).thenReturn(products);
    }

    @When("faço uma requisição GET para listar todos os produtos")
    public void faco_uma_requisicao_get_para_listar_produtos() {
        response = productController.listProducts(0, 10);  // Página 0 e tamanho 10
    }

    @Then("a resposta deve ter status {int} e conter a lista de produtos")
    public void a_resposta_deve_ter_status_e_conter_a_lista_de_produtos(int statusCode) {
        assertEquals(statusCode, response.getStatusCodeValue());
        assertTrue(((Page<Product>) response.getBody()).getContent().size() > 0);
    }

    @Given("um novo produto válido")
    public void um_novo_produto_valido() {
        productService = mock(ProductServicePort.class);
        productController = new ProductControllerImpl(productService);

        ProductRequest request = new ProductRequest();
        request.setId_product(UUID.randomUUID());
        request.setName("Novo Produto");
        request.setPrice(new BigDecimal("200.00"));
        request.setAmount(20);

        SaveProductResponse responseMock = new SaveProductResponse(UUID.randomUUID());

        when(productService.saveProduct(any(ProductRequest.class))).thenReturn(responseMock);
    }

    @When("faço uma requisição POST para {string}")
    public void faco_uma_requisicao_post_para(String endpoint) {
        response = productController.saveProduct(new ProductRequest());
    }

    @And("deve conter um ID de produto válido")
    public void deve_conter_um_id_de_produto_valido() {
        assertTrue(((SaveProductResponse) response.getBody()).getId_product() != null);
    }
}
