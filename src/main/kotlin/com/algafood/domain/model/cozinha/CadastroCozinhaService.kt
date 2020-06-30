package com.algafood.domain.model.cozinha

import com.algafood.application.cozinha.CadastrarCozinhaCommand
import com.algafood.domain.model.EntidadeEmUsoException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
class CadastroCozinhaService(val cozinhaRepository: CozinhaRepository) {

    fun salvar(command: CadastrarCozinhaCommand) : Cozinha {
        val cozinhaExists = this.cozinhaRepository.existsCozinhaByNomeIgnoreCase(command.nome)
        if (cozinhaExists) throw CozinhaExistenteExeception("Cozinha já cadastrada com nome: ${command.nome}")
        val cozinha = Cozinha(command.nome)
        return this.cozinhaRepository.save(cozinha)
    }

    fun remover(cozinhaId: Long) {
        try {
            this.cozinhaRepository.deleteById(cozinhaId)
        } catch (e: EmptyResultDataAccessException) {
            throw CozinhaNaoEncontradaException("Não existe um cadastro de cozinha com código $cozinhaId")
        } catch (e: DataIntegrityViolationException) {
            throw CozinhaEmUsoException(cozinhaId, cause = e)
        }
    }
}
