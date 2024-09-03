package de.kairenken.parzellenwetter.infrastructure.wetter.repository

import java.time.LocalDateTime
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WetterJpaRepository : JpaRepository<WetterEntity, UUID> {

    fun findAllByZeitpunktBetweenOrderByZeitpunkt(
        from: LocalDateTime,
        to: LocalDateTime
    ): List<WetterEntity>
}