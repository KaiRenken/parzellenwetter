package de.kairenken.parzellenwetter.infrastructure.notiz

import de.kairenken.parzellenwetter.infrastructure.notiz.entity.NotizEntity
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface NotizJpaRepository : JpaRepository<NotizEntity, UUID>