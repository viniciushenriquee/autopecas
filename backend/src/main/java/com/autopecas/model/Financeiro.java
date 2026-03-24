package com.autopecas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "financeiro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Financeiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_financeiro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimentacao tipo;

    @Column(length = 255)
    private String descricao;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(name = "data_movimento", insertable = false, updatable = false)
    private LocalDateTime dataMovimento;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_compra")
    private Compra compra;
}
