package com.autopecas.controller;

import com.autopecas.model.*;
import com.autopecas.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PedidoController.class)
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper objectMapper;

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
        pedido.setId_pedido(1L);
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
    void deveCriarPedidoComSucesso() throws Exception {
        when(pedidoService.criarPedido(any(Pedido.class))).thenReturn(pedido);

        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_pedido").value(1L))
                .andExpect(jsonPath("$.valorTotal").value(200.00));
    }
}
