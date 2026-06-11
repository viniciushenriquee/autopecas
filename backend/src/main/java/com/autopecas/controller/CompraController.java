package com.autopecas.controller;

import com.autopecas.model.Compra;
import com.autopecas.service.CompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    @PostMapping
    public ResponseEntity<Compra> criarCompra(@RequestBody Compra compra) {
        Compra novaCompra = compraService.criarCompra(compra);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCompra);
    }

    @GetMapping
    public ResponseEntity<List<Compra>> listarTodas() {
        return ResponseEntity.ok(compraService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> obterPorId(@PathVariable Long id) {
        return ResponseEntity.ok(compraService.obterPorId(id));
    }

    /**
     * Endpoint para confirmar o recebimento físico dos produtos.
     * Isso irá atualizar o estoque e gerar o financeiro.
     */
    @PutMapping("/{id}/receber")
    public ResponseEntity<Compra> receberCompra(@PathVariable Long id) {
        Compra compraRecebida = compraService.receberCompra(id);
        return ResponseEntity.ok(compraRecebida);
    }
}
