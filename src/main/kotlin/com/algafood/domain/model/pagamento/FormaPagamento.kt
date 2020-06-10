package com.algafood.domain.model.pagamento

import javax.persistence.*

@Entity
@Table(name = "forma_pagamento")
data class FormaPagamento(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        val descricao: String
)