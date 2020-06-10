package com.algafood.domain.model.cozinha

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CozinhaRepository : JpaRepository<Cozinha, Long> {

    fun existsCozinhaByNomeIgnoreCase(nome: String) : Boolean
}