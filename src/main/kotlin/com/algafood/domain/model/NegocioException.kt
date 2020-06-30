package com.algafood.domain.model

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.BAD_REQUEST)
open class NegocioException(msg: String, cause: Throwable?) : RuntimeException(msg, cause)
