package com.algafood.port.adapter

import com.algafood.application.cozinha.CadastrarCozinhaCommand
import com.algafood.domain.model.EntidadeEmUsoException
import com.algafood.domain.model.EntidadeNaoEncontradaException
import com.algafood.domain.model.cozinha.CadastroCozinhaService
import com.algafood.domain.model.cozinha.Cozinha
import com.algafood.domain.model.cozinha.CozinhaRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cozinhas")
class CozinhaResource(val cozinhaRepository: CozinhaRepository, val cadastroCozinha: CadastroCozinhaService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun cadastrar(@RequestBody command: CadastrarCozinhaCommand): Cozinha {
        return cadastroCozinha.salvar(command)
    }

    @GetMapping("/{cozinhaId}")
    fun buscarPorId(@PathVariable cozinhaId: Long): ResponseEntity<Cozinha> {
        return cozinhaRepository.findById(cozinhaId)
                .map{ ResponseEntity.ok(it) }
                .orElseGet{ ResponseEntity.notFound().build() }
    }

    @PutMapping("/{cozinhaId}")
    fun atualizar(@PathVariable cozinhaId: Long, @RequestBody cozinha: Cozinha): ResponseEntity<Cozinha> {
        return cozinhaRepository.findById(cozinhaId)
                .map { it.copy(nome = cozinha.nome) }
                .map { cozinhaRepository.save(it) }
                .map { ResponseEntity.ok(it) }
                .orElseGet { ResponseEntity.notFound().build() }
    }

    @DeleteMapping("/{cozinhaId}")
    fun remover(@PathVariable cozinhaId: Long) : ResponseEntity<Unit> {
        return try {
            cadastroCozinha.remover(cozinhaId)
            ResponseEntity.noContent().build()
        } catch (e: EntidadeEmUsoException) {
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        } catch (e: EntidadeNaoEncontradaException) {
            ResponseEntity.notFound().build()
        }
    }
}