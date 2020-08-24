package com.zed.pontointeligente.controllers

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.zed.pontointeligente.documents.Funcionario
import com.zed.pontointeligente.documents.Lancamento
import com.zed.pontointeligente.dtos.LancamentoDto
import com.zed.pontointeligente.enums.PerfilEnum
import com.zed.pontointeligente.enums.TipoEnum
import com.zed.pontointeligente.services.FuncionarioService
import com.zed.pontointeligente.services.LancamentoService
import com.zed.pontointeligente.utils.SenhaUtils
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.text.SimpleDateFormat
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureDataMongo
class LancamentoControllerTest {

    @Autowired
    private val mvc: MockMvc? = null

    @MockBean
    private val lancamentoService: LancamentoService? = null

    @MockBean
    private val funcionarioService: FuncionarioService? = null

    private val urlBase: String = "/api/lancamentos/"
    private val idFuncionario: String = "10"
    private val idLancamento: String = "1"
    private val tipo: String = TipoEnum.INICIO_TRABALHO.name
    private val data: Date = Date()

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @Test
    @WithMockUser
    @Throws(Exception::class)
    fun testCadastrarLancamento() {
        val lancamento: Lancamento = obterDadosLancamento()
        BDDMockito.given<Funcionario>(funcionarioService?.buscarPorId(idFuncionario))
                .willReturn(funcionario())
        BDDMockito.given(lancamentoService?.persistir(obterDadosLancamento()))
                .willReturn(lancamento)

        mvc!!.perform(MockMvcRequestBuilders.post(urlBase)
                .content(obterJsoRequisicaoPost())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.tipo").value(tipo))
                .andExpect(jsonPath("$.data.data").value(dateFormat.format(data)))
                .andExpect(jsonPath("$.data.funcionarioId").value(idFuncionario))
                .andExpect(jsonPath("$.erros").isEmpty())
    }

    @Test
    @WithMockUser
    @Throws(Exception::class)
    fun testRemoverLancamento() {
        BDDMockito.given<Lancamento>(lancamentoService?.buscarPorId(idLancamento))
                .willReturn(obterDadosLancamento())

        mvc!!.perform(MockMvcRequestBuilders.delete(urlBase + idLancamento)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)

    }

//    @Test
//    @WithMockUser
//    @Throws(Exception::class)
//    fun testRemoverLancamentoAcessoNegado() {
//        BDDMockito.given<Lancamento>(lancamentoService?.buscarPorId(idLancamento))
//                .willReturn(obterDadosLancamento())
//
//        mvc!!.perform(MockMvcRequestBuilders.delete(urlBase + idLancamento)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isForbidden)
//    }

    @Throws(JsonProcessingException::class)
    private fun obterJsoRequisicaoPost(): String {
        val lancamentoDto: LancamentoDto = LancamentoDto(
                dateFormat.format(data), tipo, "Descrição",
                "1.234,4.234", idFuncionario)
        val mapper = ObjectMapper()
        return mapper.writeValueAsString(lancamentoDto)
    }

    private fun obterDadosLancamento(): Lancamento =
            Lancamento(data, TipoEnum.valueOf(tipo), idFuncionario,
                    "Descrição", "1.234,4.234", idLancamento)

    private fun funcionario(): Funcionario =
            Funcionario("Nome", "email@email.com", SenhaUtils().gerarBcrypt("123456"),
            "3231545665516", PerfilEnum.ROLE_USUARIO, idFuncionario)
}