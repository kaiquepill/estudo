package com.zed.pontointeligente.repository

import com.zed.pontointeligente.documents.Funcionario
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface FuncionarioRepository : MongoRepository<Funcionario, String> {
    fun findByEmail(email: String): Funcionario?
    fun findByCpf(cpf: String): Funcionario?
}