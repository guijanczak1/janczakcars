package br.com.dealership.janczakcars.adapter.output.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "order_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_order")
    private Order id_order;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product id_product;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDateTime created_at;

    @Column(nullable = false)
    private LocalDateTime update_at;

    @PrePersist // Gera o UUID antes de salvar
    public void gerarUUID() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }
}
