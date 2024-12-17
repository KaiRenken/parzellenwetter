package de.kairenken.parzellenwetter.application.wetter.dto

import de.kairenken.parzellenwetter.domain.wetter.Wetter
import java.time.LocalDateTime

data class ExtremwerteDto(
    val extremTemperaturen: ExtremTemperaturenDto?
) {

    data class ExtremTemperaturenDto(
        val minimum: ExtremTemperaturDto,
        val maximum: ExtremTemperaturDto
    ) {

        data class ExtremTemperaturDto(
            val wert: Int,
            val zeitpunkt: LocalDateTime,
            val wetterId: Wetter.Id
        )
    }
}