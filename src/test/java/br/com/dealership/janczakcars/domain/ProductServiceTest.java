package br.com.dealership.janczakcars.domain;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.dealership.janczakcars.adapter.input.product.dto.ProductRequest;
import br.com.dealership.janczakcars.adapter.input.product.dto.SaveProductResponse;
import br.com.dealership.janczakcars.adapter.output.models.Product;
import br.com.dealership.janczakcars.domain.mappers.ProductMapper;
import br.com.dealership.janczakcars.domain.services.ProductServiceImpl;
import br.com.dealership.janczakcars.port.output.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductRequest productRequest;
    private UUID productId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productId = UUID.randomUUID();
        productRequest = new ProductRequest(productId,"Product Test", new BigDecimal(100.0), 1);
        product = new Product();
        product.setId_product(productId);
        product.setName("Product Test");
        product.setPrice(new BigDecimal(100.0));
        product.setAmount(1);
        product.setCreated_at(LocalDateTime.now());
        product.setUpdate_at(LocalDateTime.now());
    }

    @Test
    void testGetProductByIdProductFound() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProductById(productId);

        assertTrue(result.isPresent());
        assertEquals(productId, result.get().getId_product());
        assertEquals("Product Test", result.get().getName());
    }

    @Test
    void testGetProductByIdProductNotFound() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        Optional<Product> result = productService.getProductById(productId);

        assertFalse(result.isPresent());
    }

    @Test
    void testListProducts() {
        Product product1 = new Product();
        product1.setId_product(UUID.randomUUID());
        product1.setName("Product 1");
        product1.setPrice(BigDecimal.valueOf(100.00));
        product1.setAmount(10);
        product1.setCreated_at(LocalDateTime.now());
        product1.setUpdate_at(LocalDateTime.now());

        Product product2 = new Product();
        product2.setId_product(UUID.randomUUID());
        product2.setName("Product 2");
        product2.setPrice(BigDecimal.valueOf(150.00));
        product2.setAmount(20);
        product2.setCreated_at(LocalDateTime.now());
        product2.setUpdate_at(LocalDateTime.now());

        List<Product> productList = Arrays.asList(product1, product2);
        Page<Product> productPage = new PageImpl<>(productList);

        when(productRepository.findAll(PageRequest.of(0, 10))).thenReturn(productPage);

        Page<Product> result = productService.listProducts(PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("Product 1", result.getContent().get(0).getName());
        assertEquals("Product 2", result.getContent().get(1).getName());
    }

    @Test
    void testSaveProduct() {
        when(productMapper.mapperProductToEntity(productRequest)).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        SaveProductResponse response = productService.saveProduct(productRequest);

        assertNotNull(response);
        assertEquals(productId, response.getId_product());
    }

}
