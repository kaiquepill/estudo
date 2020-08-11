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
class PontointeligenteApplication(
		val empresaRepository: EmpresaRepository,
		val funcionarioRepository: FuncionarioRepository
		) : CommandLineRunner {
	override fun run(vararg args: String?) {
		empresaRepository.deleteAll()
		funcionarioRepository.deleteAll()

		var empresa : Empresa = Empresa("Empresa", "10443887000146")
		empresa = empresaRepository.save(empresa)

		var admin : Funcionario = Funcionario(
				"Admin",
				"admin@empresa.com",
				SenhaUtils().gerarBcrypt("123456"),
				"25708317000",
				PerfilEnum.ROLE_ADMIN,
				empresa.id!!
			)
		admin = funcionarioRepository.save(admin)

		var funcionario: Funcionario = Funcionario(
				"Funcionario",
				"funcionario@empresa.com",
				SenhaUtils().gerarBcrypt("123456"),
				"4443254441557",
				PerfilEnum.ROLE_USUARIO,
				empresa.id!!
		)
		funcionario = funcionarioRepository.save(funcionario)

		println("Empresa ID: " + empresa.id)
		println("Admin ID: " + admin.id)
		println("Funcionario ID:" + funcionario.id)
	}

}

fun main(args: Array<String>) {
	SpringApplication.run(PontointeligenteApplication::class.java, *args)
}
