package br.com.dealership.janczakcars.domain.port;

import br.com.dealership.janczakcars.adapter.input.product.dto.ProductRequest;
import br.com.dealership.janczakcars.adapter.input.product.dto.SaveProductResponse;
import br.com.dealership.janczakcars.adapter.output.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

public interface ProductServicePort {

    public Optional<Product> getProductById(UUID id);

    public Page<Product> listProducts(PageRequest pageRequest);

    public SaveProductResponse saveProduct(ProductRequest request);
}
