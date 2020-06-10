package com.algafood.domain.model.restaurante

import com.algafood.domain.model.UNDEFINED_ID
import java.io.Serializable
import javax.persistence.Embeddable
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Embeddable
data class RestauranteId(

        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long
) : Serializable {

        val isUndefined: Boolean = id == UNDEFINED_ID
}
