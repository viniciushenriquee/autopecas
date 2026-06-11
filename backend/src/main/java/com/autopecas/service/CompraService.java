package com.autopecas.service;

import com.autopecas.model.Compra;
import com.autopecas.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CompraService {
    
    @Autowired
    private CompraRepository compraRepository;
    
    @Transactional
    public Compra criarCompra(Compra compra) {
        // Aqui você pode adicionar lógica para validar a compra, atualizar o estoque, etc.
        return compraRepository.save(compra);
    }
    @Transactional
    public List<Compra> listarTodos() {
        return compraRepository.findAll();
    }

    @Transactional
    public Compra obterPorId(Long id) {
        return compraRepository.findById(id).orElseThrow(() -> new RuntimeException("Compra não encontrada"));
    }
}
