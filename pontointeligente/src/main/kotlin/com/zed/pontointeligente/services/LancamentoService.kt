package com.zed.pontointeligente.services

import com.zed.pontointeligente.documents.Lancamento
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
interface LancamentoService {
    fun buscarPorFuncionario(funcionarioId: String, pageRequest: PageRequest): Page<Lancamento>
    fun buscarPorId(id: String): Lancamento?
    fun persistir(lancamento: Lancamento): Lancamento
    fun remover(id: String)
}