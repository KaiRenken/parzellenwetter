package de.kairenken.parzellenwetter.application.wetter

import de.kairenken.parzellenwetter.application.wetter.dto.ExtremwerteDto
import de.kairenken.parzellenwetter.domain.wetter.wetterFixture
import de.kairenken.parzellenwetter.domain.wetter.wetterIdFixture

val extremwerteDtoFixture = ExtremwerteDto(
    extremTemperaturen = ExtremwerteDto.ExtremTemperaturenDto(
        minimum = ExtremwerteDto.ExtremTemperaturenDto.ExtremTemperaturDto(
            wert = wetterFixture.temperatur!!,
            wetterId = wetterIdFixture,
            zeitpunkt = wetterFixture.zeitpunkt
        ),
        maximum = ExtremwerteDto.ExtremTemperaturenDto.ExtremTemperaturDto(
            wert = wetterFixture.temperatur!!,
            wetterId = wetterIdFixture,
            zeitpunkt = wetterFixture.zeitpunkt
        )
    )
)