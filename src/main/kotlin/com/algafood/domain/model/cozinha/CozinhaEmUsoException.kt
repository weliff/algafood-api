package com.algafood.domain.model.cozinha

import com.algafood.domain.model.EntidadeEmUsoException


data class CozinhaEmUsoException(val cozinhaId: Long, override val cause: Throwable? = null) :
        EntidadeEmUsoException("Cozinha de código $cozinhaId não pode ser removida, pois está em uso", cause)