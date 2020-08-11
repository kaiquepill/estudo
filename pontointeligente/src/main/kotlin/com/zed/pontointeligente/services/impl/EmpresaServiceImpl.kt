package com.zed.pontointeligente.services.impl

import com.zed.pontointeligente.documents.Empresa
import com.zed.pontointeligente.repository.EmpresaRepository
import com.zed.pontointeligente.services.EmpresaService
import org.springframework.stereotype.Service

@Service
class EmpresaServiceImpl(val empresaRepository: EmpresaRepository) : EmpresaService {
    override fun buscarPorCnpj(cnpj: String): Empresa? = empresaRepository.findByCnpj(cnpj)
    override fun persistir(empresa: Empresa): Empresa = empresaRepository.save(empresa)
}