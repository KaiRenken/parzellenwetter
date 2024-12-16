package de.kairenken.parzellenwetter.infrastructure.wetter.rest.dto

import java.time.LocalDateTime
import java.util.UUID

data class ReadExtremwerteDto(
    val temperatur: ExtremTemperaturDto,
) {

    data class ExtremTemperaturDto(
        val minimum: TemperaturDto?,
        val maximum: TemperaturDto?
    )

    data class TemperaturDto(
        val wert: Int,
        val zeitpunkt: LocalDateTime,
        val wetterId: UUID
    )
}