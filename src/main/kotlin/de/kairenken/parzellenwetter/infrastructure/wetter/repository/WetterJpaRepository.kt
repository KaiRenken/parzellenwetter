package de.kairenken.parzellenwetter.infrastructure.wetter.repository

import de.kairenken.parzellenwetter.infrastructure.wetter.repository.entity.WetterEntity
import java.time.LocalDateTime
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface WetterJpaRepository : JpaRepository<WetterEntity, UUID> {

    fun findAllByZeitpunktBetweenOrderByZeitpunkt(
        from: LocalDateTime,
        to: LocalDateTime
    ): List<WetterEntity>

    fun findFirstByZeitpunktBetweenOrderByTemperaturDesc(
        from: LocalDateTime,
        to: LocalDateTime
    ): WetterEntity?

    fun findFirstByZeitpunktBetweenOrderByTemperaturAsc(
        from: LocalDateTime,
        to: LocalDateTime
    ): WetterEntity?
}