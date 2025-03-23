package br.com.dealership.janczakcars.port.output;

import br.com.dealership.janczakcars.adapter.output.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
}
