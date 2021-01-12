package com.algafood.application.restaurante

import com.algafood.domain.model.EntidadeNaoEncontradaException
import com.algafood.domain.model.cozinha.CozinhaNaoEncontradaException
import com.algafood.domain.model.cozinha.CozinhaRepository
import com.algafood.domain.model.restaurante.Restaurante
import com.algafood.domain.model.restaurante.RestauranteNaoEncontradoException
import com.algafood.domain.model.restaurante.RestauranteRepository
import org.springframework.stereotype.Service

@Service
class RestauranteApplicationService(val restauranteRepository: RestauranteRepository,
                                    val cozinhaRepository: CozinhaRepository) {

    fun salvar(aRestaurante: Restaurante) : Restaurante {
        val restaurante = this.cozinhaRepository.findById(aRestaurante.cozinhaId)
            .map { aRestaurante.copy(cozinhaId = it.id) }
            .orElseThrow {
                throw EntidadeNaoEncontradaException("Não encontrada cozinha de id ${aRestaurante.cozinhaId}") }

        return restauranteRepository.save(restaurante)
    }

    fun salvar(command: CadastroRestrauranteCommand) : Restaurante {
        val restaurante = this.cozinhaRepository.findById(command.cozinha.id)
                .map {
                    Restaurante(nome = command.nome, taxaFrete = command.taxaFrete, cozinhaId = it.id, endereco =
                    command.endereco)
                }
                .orElseThrow {
                    throw EntidadeNaoEncontradaException("Não encontrada cozinha de id ${command.cozinha.id}") }

        return restauranteRepository.save(restaurante)
    }

    fun atualizar(restauranteId: Long, restaurante: Restaurante) : Restaurante {
        return this.restauranteRepository.findById(restauranteId)
            .map {
                val cozinha = findCozinha(restaurante)
                it.copy(nome = restaurante.nome, taxaFrete = restaurante.taxaFrete, cozinhaId = cozinha.id)}
            .map { restauranteRepository.save(it) }
            .orElseThrow { throw RestauranteNaoEncontradoException("Não encontrado restaurante de id $restauranteId") }
    }

    private fun findCozinha(cozinhaId: Restaurante) = //TODO: colocar isso em um metodo de serviço de dominio?
        this.cozinhaRepository.findById(cozinhaId.cozinhaId)
            .orElseThrow { throw CozinhaNaoEncontradaException("Não encontrada cozinha de id ${cozinhaId.cozinhaId}") }
}