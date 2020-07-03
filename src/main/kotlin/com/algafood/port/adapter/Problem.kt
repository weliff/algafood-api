package com.algafood.port.adapter

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.*
import java.time.LocalDateTime
import java.time.OffsetDateTime

@JsonInclude(Include.NON_NULL)
data class Problem(
        val status: Int,
        val title: String,
        val type: String? = null,
        val detail: String? = null) {
}