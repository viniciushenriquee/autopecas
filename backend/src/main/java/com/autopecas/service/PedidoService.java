package com.autopecas.service;

import com.autopecas.model.*;
import com.autopecas.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoService produtoService;
    private final FinanceiroService financeiroService;

    @Transactional
    public Pedido criarPedido(Pedido pedido) {
        if (pedido.getItens() == null || pedido.getItens().isEmpty()) {
            throw new RuntimeException("Um pedido deve ter pelo menos um item");
        }

        // 1. Validar Estoque e Preparar Itens
        for (ItemPedido item : pedido.getItens()) {
            Produto produto = produtoService.buscarPorId(item.getProduto().getIdProduto())
                    .orElseThrow(() -> new RuntimeException("Produto com ID " + item.getProduto().getIdProduto() + " não encontrado"));

            if (produto.getEstoqueAtual() < item.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
            }

            // Atualiza o estoque do produto
            produto.setEstoqueAtual(produto.getEstoqueAtual() - item.getQuantidade());
            produtoService.salvar(produto);

            // Garante o relacionamento bidirecional
            item.setPedido(pedido);
        }

        // 2. Salva o pedido no banco
        pedido.setStatus(StatusPedido.Aberto); // Ou conforme a lógica de negócio
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        // 3. Registra a movimentação no financeiro (Receita)
        Financeiro financeiro = new Financeiro();
        financeiro.setPedido(pedidoSalvo);
        financeiro.setValor(pedidoSalvo.getValorTotal());
        financeiro.setTipo(TipoMovimentacao.Receita);
        financeiro.setDescricao("Venda - Pedido #" + pedidoSalvo.getIdPedido());
        financeiroService.registrarMovimentacao(financeiro);

        return pedidoSalvo;
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Pedido obterPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido com ID " + id + " não encontrado"));
    }
}
