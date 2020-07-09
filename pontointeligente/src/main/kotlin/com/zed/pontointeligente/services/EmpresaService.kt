package com.zed.pontointeligente.services

import com.zed.pontointeligente.documents.Empresa

interface EmpresaService {
    fun buscarPorCnpj(cnpj: String): Empresa?
    fun persistir(empresa: Empresa): Empresa
}