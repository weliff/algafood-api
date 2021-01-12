package com.algafood.application.restaurante

import com.algafood.application.model.command.CozinhaIdCommandModel
import com.algafood.application.model.command.EnderecoCommandModel
import com.algafood.domain.model.endereco.Endereco
import com.algafood.domain.model.restaurante.Restaurante
import java.math.BigDecimal

data class CadastroRestrauranteCommand (

        val nome: String,

        val taxaFrete: BigDecimal,

        val endereco: Endereco,

        val cozinha: CozinhaIdCommandModel

)