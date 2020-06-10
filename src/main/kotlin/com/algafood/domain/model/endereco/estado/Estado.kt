package com.algafood.domain.model.endereco.estado

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Estado (

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        val nome: String
)