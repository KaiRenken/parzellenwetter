package de.kairenken.parzellenwetter.infrastructure.notiz.repository

import de.kairenken.parzellenwetter.infrastructure.notiz.repository.entity.NotizEntity
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface NotizJpaRepository : JpaRepository<NotizEntity, UUID>