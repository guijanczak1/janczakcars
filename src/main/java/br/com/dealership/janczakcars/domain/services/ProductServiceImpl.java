package br.com.dealership.janczakcars.domain.services;

import br.com.dealership.janczakcars.adapter.input.product.dto.ProductRequest;
import br.com.dealership.janczakcars.adapter.input.product.dto.SaveProductResponse;
import br.com.dealership.janczakcars.adapter.output.models.Product;
import br.com.dealership.janczakcars.domain.mappers.ProductMapper;
import br.com.dealership.janczakcars.domain.port.ProductServicePort;
import br.com.dealership.janczakcars.port.output.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductServicePort {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Optional<Product> getProductById(UUID id) {
        return productRepository.findById(id);
    }

    @Override
    public Page<Product> listProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);
    }

    @Override
    public SaveProductResponse saveProduct(ProductRequest request) {

        Product product = productRepository.save(productMapper.mapperProductToEntity(request));

        return new SaveProductResponse(product.getId_product());
    }
}
