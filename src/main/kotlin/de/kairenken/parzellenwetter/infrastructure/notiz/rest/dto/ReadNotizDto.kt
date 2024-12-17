package de.kairenken.parzellenwetter.infrastructure.notiz.rest.dto

import java.time.LocalDateTime
import java.util.UUID

data class ReadNotizDto(
    val id: UUID,
    val verfasser: String,
    val nachricht: String,
    val zeitpunkt: LocalDateTime
)