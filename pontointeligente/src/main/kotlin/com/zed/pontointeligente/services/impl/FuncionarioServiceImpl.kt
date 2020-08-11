package com.zed.pontointeligente.services.impl

import com.zed.pontointeligente.documents.Funcionario
import com.zed.pontointeligente.repository.FuncionarioRepository
import com.zed.pontointeligente.services.FuncionarioService
import org.springframework.stereotype.Service

@Service
class FuncionarioServiceImpl(val funcionarioRepository: FuncionarioRepository) : FuncionarioService {
    override fun persistir(funcionario: Funcionario): Funcionario = funcionarioRepository.save(funcionario)
    override fun buscarPorCpf(cpf: String): Funcionario? = funcionarioRepository.findByCpf(cpf)
    override fun buscarPorEmail(email: String): Funcionario? = funcionarioRepository.findByEmail(email)
    override fun buscarPorId(id: String): Funcionario? = funcionarioRepository.findById(id).get()
}