package com.zed.pontointeligente.repository

import com.zed.pontointeligente.documents.Empresa
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface EmpresaRepository : MongoRepository<Empresa, String> {
    fun findByCnpj(cnpj: String): Empresa?
}