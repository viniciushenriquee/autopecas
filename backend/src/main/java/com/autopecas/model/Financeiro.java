package com.autopecas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "financeiro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Financeiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_financeiro")
    private Long idFinanceiro;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Financeiro that)) return false;
        return idFinanceiro != null && idFinanceiro.equals(that.getIdFinanceiro());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
