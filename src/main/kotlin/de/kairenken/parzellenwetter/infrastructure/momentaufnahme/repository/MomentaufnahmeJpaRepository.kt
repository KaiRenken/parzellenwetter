package de.kairenken.parzellenwetter.infrastructure.momentaufnahme.repository

import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MomentaufnahmeJpaRepository : JpaRepository<MomentaufnahmeEntity, UUID>