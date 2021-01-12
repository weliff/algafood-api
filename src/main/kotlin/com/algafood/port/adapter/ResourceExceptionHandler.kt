package com.algafood.port.adapter

import com.algafood.domain.model.EntidadeEmUsoException
import com.algafood.domain.model.EntidadeNaoEncontradaException
import com.algafood.domain.model.NegocioException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.databind.exc.PropertyBindingException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.Exception

@ControllerAdvice
@Component
class ResourceExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        if (ex is InvalidFormatException) {
            return handleInvalidFormatException(ex, headers, status, request)
        }
        if (ex is PropertyBindingException) {
            return handlePropertyBindingException(ex, headers, status, request)
        }
        val problema = Problem(status.value(), "Mensagem incompreensível",
                "http://www.algafood.com/mensagem-incompreensivel",
                "O corpo da requisição está inválido. Verifique a sintaxe")
        return handleExceptionInternal(ex, problema, headers, status, request)
    }

    private fun handlePropertyBindingException(ex: PropertyBindingException, headers: HttpHeaders, status: HttpStatus,
                                               request: WebRequest): ResponseEntity<Any> {
        val propertyPath = ex.path
                .map { it.fieldName }
                .joinToString(".")

        val problema = Problem(status.value(),
                "Mensagem incompreensível",
                "http://www.algafood.com/mensagem-incompreensivel",
                "A propriedade '$propertyPath' recebeu o valor '${ex.propertyName}' que é do tipo inválido. Corrija e " +
                        "informe um valor compatível com o tipo ${ex.targetType.simpleName}")

        return handleExceptionInternal(ex, problema, headers, status, request)
    }

    fun handleInvalidFormatException(ex: InvalidFormatException, headers: HttpHeaders, status: HttpStatus,
                                     request: WebRequest): ResponseEntity<Any> {
        val propertyPath = ex.path
                .map { it.fieldName }
                .joinToString(".")

        val problema = Problem(status.value(),
                "Mensagem incompreensível",
                "http://www.algafood.com/mensagem-incompreensivel",
                "A propriedade '$propertyPath' recebeu o valor '${ex.value}' que é do tipo inválido. Corrija e " +
                        "informe um valor compatível com o tipo ${ex.targetType.simpleName}")

        return handleExceptionInternal(ex, problema, headers, status, request)
    }

    @ExceptionHandler(EntidadeNaoEncontradaException::class)
    fun handleEntidadeNaoEncontradaException(ex: EntidadeNaoEncontradaException, request: WebRequest) {
        val problema = Problem(HttpStatus.NOT_FOUND.value(), "Entidade não encontrada", "http://www.algafood" +
                ".com/entidade-nao-encontrada", ex.message)
        handleExceptionInternal(ex, problema, HttpHeaders.EMPTY, HttpStatus.NOT_FOUND, request)
    }

    @ExceptionHandler(EntidadeEmUsoException::class)
    fun handleEntidadeEmUsoException(ex: EntidadeEmUsoException, request: WebRequest) {
        val problema = Problem(HttpStatus.NOT_FOUND.value(), "Entidade em uso",
                "http://www.algafood.com/entidade-em-uso", ex.message)
        handleExceptionInternal(ex, problema, HttpHeaders.EMPTY, HttpStatus.CONFLICT, request)
    }

    @ExceptionHandler(NegocioException::class)
    fun handleNegocioException(ex: NegocioException, request: WebRequest) {
        val problema = Problem(HttpStatus.NOT_FOUND.value(), "Violação de regra de negócio",
                "http://www.algafood.com/erro-negocio", ex.message)
        handleExceptionInternal(ex, problema, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, request)
    }

    override fun handleExceptionInternal(ex: Exception, body: Any?, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val newBody = body.let {
            if (it == null) Problem(status.value(), status.reasonPhrase)
            else if (it is String) Problem(status.value(), it)
            else it
        }
        return super.handleExceptionInternal(ex, newBody, headers, status, request)
    }

}