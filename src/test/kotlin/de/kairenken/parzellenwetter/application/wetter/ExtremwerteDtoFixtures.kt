package de.kairenken.parzellenwetter.application.wetter

import de.kairenken.parzellenwetter.application.wetter.dto.ExtremwerteDto
import de.kairenken.parzellenwetter.domain.wetter.Wetter
import de.kairenken.parzellenwetter.domain.wetter.wetterFixture
import java.util.UUID

val extremwerteDtoFixture = ExtremwerteDto(
    extremTemperaturen = Pair(
        wetterFixture,
        wetterFixture.copy(
            id = Wetter.Id(UUID.randomUUID()),
            temperatur = wetterFixture.temperatur!! + 1,
            zeitpunkt = wetterFixture.zeitpunkt.plusMinutes(1L)
        )
    )
)