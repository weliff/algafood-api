package com.algafood.domain.model.endereco

import com.algafood.domain.model.endereco.cidade.Cidade
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Embeddable
data class Endereco (

    @Column(name = "endereco_cep")
    val cep: String,

    @Column(name = "endereco_logradouro")
    val logradouro: String,

    @Column(name = "endereco_numero")
    val numero: String,

    @Column(name = "endereco_complemento")
    val complemento: String,

    @Column(name = "endereco_bairro")
    val bairro: String,

    @Column(name = "endereco_cidade_id")
    val cidadeId: Long
)