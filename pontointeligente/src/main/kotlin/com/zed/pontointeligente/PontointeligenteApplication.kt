package com.zed.pontointeligente

import com.zed.pontointeligente.documents.Empresa
import com.zed.pontointeligente.documents.Funcionario
import com.zed.pontointeligente.enums.PerfilEnum
import com.zed.pontointeligente.repository.EmpresaRepository
import com.zed.pontointeligente.repository.FuncionarioRepository
import com.zed.pontointeligente.utils.SenhaUtils
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class PontointeligenteApplication


fun main(args: Array<String>) {
	SpringApplication.run(PontointeligenteApplication::class.java, *args)
}
