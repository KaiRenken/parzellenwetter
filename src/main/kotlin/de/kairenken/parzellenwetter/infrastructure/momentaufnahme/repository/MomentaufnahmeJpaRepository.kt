package de.kairenken.parzellenwetter.infrastructure.momentaufnahme.repository

import java.time.LocalDateTime
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MomentaufnahmeJpaRepository : JpaRepository<MomentaufnahmeEntity, UUID> {

    fun findAllByObsTimeLocalBetweenOrderByObsTimeLocal(
        from: LocalDateTime,
        to: LocalDateTime
    ): List<MomentaufnahmeEntity>
}