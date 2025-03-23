package br.com.dealership.janczakcars.adapter.output.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "`order`")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    private UUID id_order;

    @Column(nullable = true)
    private String order_description;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private BigDecimal total_price;

    @Column(nullable = false)
    private LocalDateTime created_at;

    @Column(nullable = false)
    private LocalDateTime update_at;

    @PrePersist // Gera o UUID antes de salvar
    public void gerarUUID() {
        if (id_order == null) {
            id_order = UUID.randomUUID();
        }
    }

}
