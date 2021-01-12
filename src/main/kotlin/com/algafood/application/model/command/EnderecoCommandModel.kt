package com.algafood.application.model.command

import com.algafood.domain.model.endereco.cidade.Cidade
import javax.persistence.Column
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

class EnderecoCommandModel(

        val cep: String,

        val logradouro: String,

        val numero: String,

        val complemento: String,

        val bairro: String,

        val cidade: CidadeIdCommandModel
) {
}