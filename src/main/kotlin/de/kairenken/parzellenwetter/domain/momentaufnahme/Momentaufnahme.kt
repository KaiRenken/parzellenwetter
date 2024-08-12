package de.kairenken.parzellenwetter.domain.momentaufnahme

import java.time.LocalDateTime
import java.util.UUID

class Momentaufnahme(
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
    val niederschlagDurchschnitt: Float?
)