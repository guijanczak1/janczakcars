package br.com.dealership.janczakcars.port.output;

import br.com.dealership.janczakcars.adapter.output.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
