package com.algafood.domain.model.restaurante

import com.algafood.domain.model.UNDEFINED_ID
import com.algafood.domain.model.cozinha.CozinhaId
import com.algafood.domain.model.endereco.Endereco
import com.algafood.domain.model.pagamento.FormaPagamento
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.UpdateTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Min

@DynamicUpdate
@Entity
data class Restaurante(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = UNDEFINED_ID,

        val nome: String,

        val taxaFrete: BigDecimal,

        @JsonIgnore
        @Embedded
        val endereco: Endereco,

        @field:Min(1)
        val cozinhaId: Long, //Referenciado outro agregado pelo ID

        @JsonIgnore
        @ManyToMany
        @JoinTable(name = "restaurante_forma_pagamento", joinColumns = [JoinColumn(name = "restaurante_id")],
                inverseJoinColumns = [JoinColumn(name = "forma_pagamento_id")])
        val formasPagamento: List<FormaPagamento> = listOf(), // Nao da pr referenciar por ID. Restaurante é o
        // agregado dono da relação, mas uma forma de pagamento não pode ser alterada quando um restaurante for
        // alterado na mesma transação

        @JsonIgnore
        @CreationTimestamp
        val dataCadastro: LocalDateTime, // = LocalDateTime.now(), //atribuir no construtor?

        @JsonIgnore
        @UpdateTimestamp
        val dataAtualizacao: LocalDateTime
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Restaurante

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}