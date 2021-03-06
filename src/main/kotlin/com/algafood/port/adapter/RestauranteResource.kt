package com.algafood.port.adapter

import com.algafood.application.restaurante.CadastroRestrauranteCommand
import com.algafood.domain.model.cozinha.CozinhaNaoEncontradaException
import com.algafood.application.restaurante.RestauranteApplicationService
import com.algafood.domain.model.restaurante.Restaurante
import com.algafood.domain.model.restaurante.RestauranteNaoEncontradoException
import com.algafood.domain.model.restaurante.RestauranteRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.text.CaseUtils
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.util.ClassUtils
import org.springframework.util.ReflectionUtils
import org.springframework.util.ResourceUtils
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/restaurantes")
class RestauranteResource(val restauranteRepository: RestauranteRepository,
                          val restauranteApplicationService: RestauranteApplicationService,
                          val objectMapper: ObjectMapper) {

    @GetMapping
    fun listar() =  restauranteRepository.findAll()

    @GetMapping("/{restauranteId}")
    fun buscar(@PathVariable restauranteId: Long) : ResponseEntity<Restaurante> {
        return restauranteRepository.findById(restauranteId)
            .map { ok(it) }
            .orElseGet { notFound().build() }
    }

    @PostMapping
    fun adicionar(@RequestBody @Valid restaurante: CadastroRestrauranteCommand) : ResponseEntity<Restaurante> {
//        return ResponseEntity.status(HttpStatus.CREATED).body(restauranteApplicationService.salvar(restaurante))
        println(restaurante)
        return ok().build();
    }

    @PutMapping("/{restauranteId}")
    fun atualizar(@PathVariable restauranteId: Long, @RequestBody @Valid restaurante: Restaurante) : Restaurante {
        return restauranteApplicationService.atualizar(restauranteId, restaurante)
    }

    @PatchMapping("/{restauranteId}")
    fun atualizarParcial(@PathVariable restauranteId: Long, @RequestBody campos: Map<String, Any>): ResponseEntity<Restaurante> {
        return restauranteRepository.findById(restauranteId)
            .map { merge(campos, it) }
            .map { restauranteRepository.save(it) }
            .map { ok(it) }
            .orElseGet { notFound().build() }
    }

    private fun merge(campos: Map<String, Any>, aRestaurante: Restaurante) : Restaurante {
        //Melhor utilizar um comando com campos opcionais
        val restaurante = aRestaurante.copy()
        campos.forEach { campo, valor ->
            val campoCamelCase = CaseUtils.toCamelCase(campo, false, '_')
            val field = ReflectionUtils.findField(Restaurante::class.java, campoCamelCase)
            field?.run {
                this.trySetAccessible()
                ReflectionUtils.setField(this, restaurante, valor)
            }

        }
        return restaurante
    }


    @ExceptionHandler(RestauranteNaoEncontradoException::class)
    fun handleRestauranteNaoEncontrado(e: RestauranteNaoEncontradoException) : ResponseEntity<Any> {
        return notFound().build()
    }

    @ExceptionHandler(CozinhaNaoEncontradaException::class)
    fun handleCozinhaNaoEncontrada(e: CozinhaNaoEncontradaException) : ResponseEntity<Any> {
        return badRequest().body(e.message)
    }

}