package br.com.dealership.janczakcars.port.input.product;


import br.com.dealership.janczakcars.adapter.input.product.dto.ProductRequest;
import br.com.dealership.janczakcars.adapter.input.product.dto.SaveProductResponse;
import br.com.dealership.janczakcars.adapter.output.models.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Tag(name = "ProductController")
public interface ProductControllerPort {

    @Operation(summary = "Busca Produto por ID", description = "Busca o Produto por ID na base de dados")
    @ApiResponse(responseCode = "200", description = "Ok")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ApiResponse(responseCode = "400", description = "Erro de parametro")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor")
    ResponseEntity<Product> findProductById(@PathVariable("id") UUID id);

    @Operation(summary = "Salvar Produto", description = "Salvar produto unico")
    @ApiResponse(responseCode = "200", description = "Ok")
    @ApiResponse(responseCode = "400", description = "Erro de payload")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor")
    ResponseEntity<SaveProductResponse> saveProduct(@RequestBody ProductRequest request);

}


