package com.zed.pontointeligente.controllers

import com.zed.pontointeligente.documents.Empresa
import com.zed.pontointeligente.documents.Funcionario
import com.zed.pontointeligente.dtos.CadastroPJDto
import com.zed.pontointeligente.enums.PerfilEnum
import com.zed.pontointeligente.response.Response
import com.zed.pontointeligente.services.EmpresaService
import com.zed.pontointeligente.services.FuncionarioService
import com.zed.pontointeligente.utils.SenhaUtils
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/cadastrar-pj")
class CadastroPjController (val empresaService: EmpresaService, val funcionarioService: FuncionarioService) {

    @PostMapping
    fun cadastrar(@Valid @RequestBody cadastroPJDto: CadastroPJDto, result: BindingResult):
            ResponseEntity<Response<CadastroPJDto>> {
        val response: Response<CadastroPJDto> = Response<CadastroPJDto>()

        validarDadosExistentes(cadastroPJDto, result)
        if (result.hasErrors()){
            result.allErrors.forEach { erro ->
                erro.defaultMessage?.let { response.erros.add(it) }
            }
            return ResponseEntity.badRequest().body(response)
        }

        val empresa: Empresa = converterDtoParaEmpresa(cadastroPJDto)
        empresaService.persistir(empresa)

        var funcionario: Funcionario = converterDtoParaFuncionario(cadastroPJDto, empresa)
        funcionario = funcionarioService.persistir(funcionario)
        response.data = converterCadastroPjDto(funcionario, empresa)

        return ResponseEntity.ok(response)
    }

    private fun validarDadosExistentes(cadastroPJDto: CadastroPJDto, result: BindingResult) {
        val empresa: Empresa? = empresaService.buscarPorCnpj(cadastroPJDto.cnpj)
        if (empresa != null) {
            result.addError(ObjectError("funcionario", "Empresa já existe"))
        }

        val funcionarioCpf: Funcionario? = funcionarioService.buscarPorCpf(cadastroPJDto.cpf)
        if (funcionarioCpf != null) {
            result.addError(ObjectError("funcionario", "CPF já existe"))
        }

        val funcionarioEmail: Funcionario? = funcionarioService.buscarPorEmail(cadastroPJDto.email)
        if (funcionarioEmail != null) {
            result.addError(ObjectError("funcionario", "Email já existe"))
        }
    }

    private fun converterDtoParaEmpresa(cadastroPJDto: CadastroPJDto): Empresa =
            Empresa(cadastroPJDto.razaoSocial, cadastroPJDto.cnpj)

    private fun converterDtoParaFuncionario(cadastroPJDto: CadastroPJDto, empresa: Empresa) =
            Funcionario(cadastroPJDto.nome, cadastroPJDto.email, SenhaUtils().gerarBcrypt(cadastroPJDto.senha),
            cadastroPJDto.cpf, PerfilEnum.ROLE_ADMIN, empresa.id.toString())

    private fun converterCadastroPjDto(funcionario: Funcionario, empresa: Empresa): CadastroPJDto =
            CadastroPJDto(funcionario.nome, funcionario.email, "", funcionario.cpf,
                    empresa.cnpj, empresa.razaoSocial, funcionario.id)
}