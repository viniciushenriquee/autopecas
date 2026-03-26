package com.autopecas.service;

import com.autopecas.model.*;
import com.autopecas.repository.FinanceiroRepository;
import com.autopecas.repository.PedidoRepository;
import com.autopecas.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FinanceiroRepository financeiroRepository;

    @Transactional
    public Pedido criarPedido(Pedido pedido) {
        // Para cada item no pedido, vamos verificar o estoque
        for (ItemPedido item : pedido.getItens()) {
            Produto produto = produtoRepository.findById(item.getProduto().getId_produto())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            if (produto.getEstoqueAtual() < item.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
            }

            // Atualiza o estoque do produto
            produto.setEstoqueAtual(produto.getEstoqueAtual() - item.getQuantidade());
            produtoRepository.save(produto);
        }

        // Salva o pedido no banco
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        // Registra a movimentação no financeiro
        Financeiro financeiro = new Financeiro();
        financeiro.setPedido(pedidoSalvo);
        financeiro.setValor(pedidoSalvo.getValorTotal());
        financeiro.setTipo(TipoMovimentacao.Receita);
        financeiro.setDescricao("Venda - Pedido #" + pedidoSalvo.getId_pedido());
        financeiroRepository.save(financeiro);

        return pedidoSalvo;
    }
}
