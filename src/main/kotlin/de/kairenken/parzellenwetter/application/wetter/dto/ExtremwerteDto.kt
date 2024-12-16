package de.kairenken.parzellenwetter.application.wetter.dto

import de.kairenken.parzellenwetter.domain.wetter.Wetter

data class ExtremwerteDto(
    val extremTemperaturen: Pair<Wetter?, Wetter?>
)