package com.zed.pontointeligente.repository

import com.zed.pontointeligente.documents.Lancamento
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface LancamentoRepository : MongoRepository<Lancamento, String> {
    fun findByFuncionarioId(funcionarioId: String, pageable: Pageable)
}
