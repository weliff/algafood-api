package com.algafood.port.adapter

import com.algafood.domain.model.EntidadeEmUsoException
import com.algafood.domain.model.EntidadeNaoEncontradaException
import com.algafood.domain.model.NegocioException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.Exception
import java.time.OffsetDateTime

@ControllerAdvice
@Component
class ResourceExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(EntidadeNaoEncontradaException::class)
    fun handleEntidadeNaoEncontradaException(ex: EntidadeNaoEncontradaException, request: WebRequest) {
        val problema = Problema(HttpStatus.NOT_FOUND.value(), "Entidade não encontrada", "http://www.algafood" +
                ".com/entidade-nao-encontrada", ex.message)
        handleExceptionInternal(ex, problema, HttpHeaders.EMPTY, HttpStatus.NOT_FOUND, request)
    }

    @ExceptionHandler(EntidadeEmUsoException::class)
    fun handleEntidadeEmUsoException(ex: EntidadeEmUsoException, request: WebRequest) {
        val problema = Problema(HttpStatus.NOT_FOUND.value(), "Entidade em uso",
                "http://www.algafood.com/entidade-em-uso", ex.message)
        handleExceptionInternal(ex, problema, HttpHeaders.EMPTY, HttpStatus.CONFLICT, request)
    }

    @ExceptionHandler(NegocioException::class)
    fun handleNegocioException(ex: NegocioException, request: WebRequest) {
        val problema = Problema(HttpStatus.NOT_FOUND.value(), "Violação de regra de negócio",
                "http://www.algafood.com/erro-negocio", ex.message)
        handleExceptionInternal(ex, problema, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, request)
    }

    override fun handleExceptionInternal(ex: Exception, body: Any?, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val newBody = body.let {
            if (it == null) Problema(status.value(), status.reasonPhrase)
            else if (it is String) Problema(status.value(), it)
            else it
        }
        return super.handleExceptionInternal(ex, newBody, headers, status, request)
    }

}