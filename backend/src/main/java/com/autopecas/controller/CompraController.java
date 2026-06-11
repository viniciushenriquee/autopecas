package com.autopecas.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.autopecas.service.CompraService;
import com.autopecas.model.Compra;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/compras")
public class CompraController {
    @Autowired
    private CompraService compraService;

    @PostMapping
    public Compra criarCompra(@RequestBody Compra compra) {
        return compraService.criarCompra(compra);
    }
}

