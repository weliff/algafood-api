package com.algafood.domain.model.endereco.cidade

import com.algafood.domain.model.endereco.estado.Estado
import javax.persistence.*

@Entity
data class Cidade (

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        val nome: String,

        @JoinColumn(name = "estado_id")
        @ManyToOne
        val estado: Estado
)