package com.autopecas.service;

import com.autopecas.model.Financeiro;
import com.autopecas.repository.FinanceiroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FinanceiroService {

    private final FinanceiroRepository financeiroRepository;

    @Transactional
    public Financeiro registrarMovimentacao(Financeiro financeiro) {
        return financeiroRepository.save(financeiro);
    }
}
