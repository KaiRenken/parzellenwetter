package de.kairenken.parzellenwetter.domain.wetter

import java.time.LocalDateTime
import java.util.UUID

val wetterFixture = Wetter(
    id = UUID.fromString("6387132b-3228-4e40-8ae7-7b79d1237947"),
    zeitpunkt = LocalDateTime.parse("2024-09-03T15:43:11"),
    temperatur = 23,
    luftfeuchtigkeit = 67,
    taupunkt = 15,
    luftdruck = 1099.9f,
    windrichtung = 145,
    windgeschwindigkeit = 60,
    windboeengeschwindigkeit = 70,
    sonnenstrahlung = 45.7f,
    uvIndex = 3.5f,
    niederschlag = 6.9f,
    niederschlagGesamt = 16.4f
)