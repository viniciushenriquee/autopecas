package com.autopecas.service;

import com.autopecas.model.*;
import com.autopecas.repository.FinanceiroRepository;
import com.autopecas.repository.PedidoRepository;
import com.autopecas.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private FinanceiroRepository financeiroRepository;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedido;
    private Produto produto;
    private Cliente cliente;
    private Usuario vendedor;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId_cliente(1L);
        cliente.setNome("Cliente Teste");

        vendedor = new Usuario();
        vendedor.setId_usuario(1L);
        vendedor.setNome("Vendedor Teste");

        produto = new Produto();
        produto.setId_produto(1L);
        produto.setNome("Peça Teste");
        produto.setPrecoVenda(new BigDecimal("100.00"));
        produto.setEstoqueAtual(10);

        pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setVendedor(vendedor);
        pedido.setStatus(StatusPedido.Aberto);
        
        ItemPedido item = new ItemPedido();
        item.setProduto(produto);
        item.setQuantidade(2);
        item.setPrecoUnitario(produto.getPrecoVenda());
        item.setSubtotal(new BigDecimal("200.00"));
        item.setPedido(pedido);

        List<ItemPedido> itens = new ArrayList<>();
        itens.add(item);
        pedido.setItens(itens);
        pedido.setValorTotal(new BigDecimal("200.00"));
    }

    @Test
    void criarPedidoComSucesso() {
        // Simulando que o produto existe no banco
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        
        // Simulando o salvamento do pedido (retorna o pedido com ID 1)
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido p = invocation.getArgument(0);
            p.setId_pedido(1L);
            return p;
        });

        // Chamada do método
        Pedido pedidoCriado = pedidoService.criarPedido(pedido);

        // Verificações (Assertions)
        assertNotNull(pedidoCriado);
        assertEquals(1L, pedidoCriado.getId_pedido());
        assertEquals(new BigDecimal("200.00"), pedidoCriado.getValorTotal());
        
        // Verifica se salvou o produto (para dar baixa no estoque), o pedido e o financeiro
        verify(produtoRepository, times(1)).save(any(Produto.class));
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
        verify(financeiroRepository, times(1)).save(any(Financeiro.class));
    }

    @Test
    void criarPedidoSemEstoqueDeveLancarExcecao() {
        produto.setEstoqueAtual(1); // Só tem 1, mas o pedido quer 2
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        // Esperamos que o sistema lance uma exceção se não houver estoque
        assertThrows(RuntimeException.class, () -> pedidoService.criarPedido(pedido));
        
        // Verifica que o pedido NUNCA foi salvo se deu erro de estoque
        verify(pedidoRepository, never()).save(any(Pedido.class));
    }
}
