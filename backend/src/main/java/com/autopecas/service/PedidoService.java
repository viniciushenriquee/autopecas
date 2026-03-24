package com.autopecas.service;

import com.autopecas.model.ItemPedido;
import com.autopecas.model.Pedido;
import com.autopecas.model.Produto;
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
        return pedidoRepository.save(pedido);
    }
}
