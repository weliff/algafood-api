package com.algafood.domain.model.restaurante

import org.springframework.data.jpa.repository.JpaRepository

interface RestauranteRepository : JpaRepository<Restaurante, Long> {
}