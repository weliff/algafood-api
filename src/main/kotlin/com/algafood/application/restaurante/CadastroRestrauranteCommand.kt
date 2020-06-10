package com.algafood.application.restaurante

import com.algafood.application.model.command.CozinhaIdCommandModel
import com.algafood.application.model.command.EnderecoCommandModel
import java.math.BigDecimal

data class CadastroRestrauranteCommand (

        val nome: String,

        val taxaFrete: BigDecimal,

        val endereco: List<EnderecoCommandModel>,

        val cozinha: CozinhaIdCommandModel
)