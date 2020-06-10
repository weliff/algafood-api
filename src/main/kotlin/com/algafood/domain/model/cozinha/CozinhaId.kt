package com.algafood.domain.model.cozinha

import java.io.Serializable
import javax.persistence.Embeddable
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Embeddable
data class CozinhaId(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long

) : Serializable