package br.com.dealership.janczakcars.adapter.input.product;

import br.com.dealership.janczakcars.adapter.input.product.dto.ProductRequest;
import br.com.dealership.janczakcars.adapter.input.product.dto.SaveProductResponse;
import br.com.dealership.janczakcars.adapter.output.models.Product;
import br.com.dealership.janczakcars.domain.port.ProductServicePort;
import br.com.dealership.janczakcars.port.input.product.ProductControllerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/product")
public class ProductControllerImpl implements ProductControllerPort {

    private final ProductServicePort productService;

    @Autowired
    public ProductControllerImpl(ProductServicePort productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Product> findProductById(@PathVariable UUID id) {
        return productService.getProductById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping
    public ResponseEntity<Page<Product>> listProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        Page<Product> products = productService.listProducts(PageRequest.of(page, size));
        return ResponseEntity.ok(products);
    }

    @PostMapping
    @Override
    public ResponseEntity<SaveProductResponse> saveProduct(@RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.saveProduct(request));
    }
}
