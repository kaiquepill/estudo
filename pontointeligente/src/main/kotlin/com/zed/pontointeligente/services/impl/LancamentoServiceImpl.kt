package com.zed.pontointeligente.services.impl

import com.zed.pontointeligente.documents.Lancamento
import com.zed.pontointeligente.repository.LancamentoRepository
import com.zed.pontointeligente.services.LancamentoService
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class LancamentoServiceImpl(val lancamentoRepository: LancamentoRepository) : LancamentoService {

    override fun buscarPorFuncionarioId(funcionarioId: String, pageRequest: PageRequest) =
            lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest)
    override fun buscarPorId(id: String): Lancamento? = lancamentoRepository.findById(id).get()
    override fun persistir(lancamento: Lancamento) = lancamentoRepository.save(lancamento)
    override fun remover(id: String) = lancamentoRepository.deleteById(id)
}