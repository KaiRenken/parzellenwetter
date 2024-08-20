package de.kairenken.parzellenwetter.infrastructure.momentaufnahme.rest

import java.time.LocalDateTime
import java.util.UUID

class ReadMomentaufnahmeDto(
    val id: UUID,
    val zeitpunkt: LocalDateTime,
    val temperatur: Int?,
    val luftfeuchtigkeit: Int?,
    val taupunkt: Int?,
    val luftdruck: Float?,
    val windrichtung: Int?,
    val windgeschwindigkeit: Int?,
    val windboeengeschwindigkeit: Int?,
    val sonnenstrahlung: Float?,
    val uvIndex: Float?,
    val niederschlag: Float?,
    val niederschlagGesamt: Float?
)