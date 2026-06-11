package com.autopecas.service;

import com.autopecas.model.*;
import com.autopecas.repository.CompraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompraService {

    private final CompraRepository compraRepository;
    private final ProdutoService produtoService;
    private final FinanceiroService financeiroService;

    @Transactional
    public Compra criarCompra(Compra compra) {
        // 1. Validar e Calcular Totais
        BigDecimal totalCompra = BigDecimal.ZERO;
        
        if (compra.getItens() == null || compra.getItens().isEmpty()) {
            throw new RuntimeException("Uma compra deve ter pelo menos um item");
        }

        for (ItemCompra item : compra.getItens()) {
            // Garante o relacionamento bidirecional
            item.setCompra(compra);
            
            // Calcula o subtotal do item (Qtd * Valor Unitário)
            BigDecimal subtotal = item.getValorUnitario().multiply(new BigDecimal(item.getQuantidade()));
            item.setTotal(subtotal);
            
            totalCompra = totalCompra.add(subtotal);
        }

        compra.setValorTotal(totalCompra);
        compra.setStatus(StatusCompra.Aberta);

        return compraRepository.save(compra);
    }

    @Transactional
    public Compra receberCompra(Long id) {
        Compra compra = obterPorId(id);

        if (compra.getStatus() != StatusCompra.Aberta) {
            throw new RuntimeException("Apenas compras em aberto podem ser recebidas");
        }

        // 1. Atualizar Estoque
        for (ItemCompra item : compra.getItens()) {
            Produto produto = item.getProduto();
            int novoEstoque = produto.getEstoqueAtual() + item.getQuantidade();
            produto.setEstoqueAtual(novoEstoque);
            produtoService.salvar(produto);
        }

        // 2. Gerar Movimentação Financeira (Contas a Pagar)
        Financeiro financeiro = new Financeiro();
        financeiro.setTipo(TipoMovimentacao.Despesa);
        financeiro.setValor(compra.getValorTotal());
        financeiro.setDescricao("Pagamento de Compra #" + compra.getIdCompra() + " - Fornecedor: " + compra.getFornecedor().getNome());
        financeiro.setCompra(compra);
        financeiroService.registrarMovimentacao(financeiro);

        // 3. Finalizar Compra
        compra.setStatus(StatusCompra.Recebida);
        return compraRepository.save(compra);
    }

    @Transactional(readOnly = true)
    public List<Compra> listarTodos() {
        return compraRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Compra obterPorId(Long id) {
        return compraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra com ID " + id + " não encontrada"));
    }
}
