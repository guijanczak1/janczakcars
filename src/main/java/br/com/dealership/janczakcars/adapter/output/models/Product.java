package br.com.dealership.janczakcars.adapter.output.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private UUID id_product;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private long amount;

    @Column(nullable = false)
    private LocalDateTime created_at;

    @Column(nullable = false)
    private LocalDateTime update_at;

    @PrePersist // Gera o UUID antes de salvar
    public void gerarUUID() {
        if (id_product == null) {
            id_product = UUID.randomUUID();
        }
    }
}

