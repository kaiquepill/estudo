package com.zed.pontointeligente.services

import com.zed.pontointeligente.documents.Funcionario
import org.springframework.stereotype.Service

@Service
interface FuncionarioService {
    fun persistir(funcionario: Funcionario): Funcionario
    fun buscarPorCpf(cpf: String): Funcionario?
    fun buscarPorEmail(email: String): Funcionario?
    fun buscarPorId(id: String): Funcionario?
}