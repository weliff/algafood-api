package com.algafood.domain.model.cozinha

import com.algafood.domain.model.UNDEFINED_ID
import org.springframework.data.domain.AbstractAggregateRoot
import javax.persistence.*


@Entity
data class Cozinha private constructor(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = UNDEFINED_ID,

        val nome: String

) : AbstractAggregateRoot<Cozinha>() {

    constructor(aNome: String) : this(nome = aNome)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cozinha

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
