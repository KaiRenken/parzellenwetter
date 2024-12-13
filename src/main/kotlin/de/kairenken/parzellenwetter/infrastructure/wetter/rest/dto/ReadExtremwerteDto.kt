package de.kairenken.parzellenwetter.infrastructure.wetter.rest.dto

import java.time.LocalDateTime
import java.util.UUID

data class ReadExtremwerteDto(
    val temperatur: TemperaturDto?,
    val luftfeuchtigkeit: LuftfeuchtigkeitDto?,
    val taupunkt: TaupunktDto?,
    val luftdruck: LuftdruckDto?,
    val windrichtung: WindrichtungDto?,
    val windgeschwindigkeit: WindgeschwindigkeitDto?,
    val windboeengeschwindigkeit: WindboeengeschwindigkeitDto?,
    val sonnenstrahlung: SonnenstrahlungDto?,
    val uvIndex: UvIndexDto?,
    val niederschlag: NiederschlagDto?,
    val niederschlagGesamt: NiederschlagGesamtDto?
) {

    data class TemperaturDto(
        val minimum: Int,
        val maximum: Int,
        val zeitpunkt: LocalDateTime,
        val wetterId: UUID
    )

    data class LuftfeuchtigkeitDto(
        val minimum: Int,
        val maximum: Int,
        val zeitpunkt: LocalDateTime,
        val wetterId: UUID
    )

    data class TaupunktDto(
        val minimum: Int,
        val maximum: Int,
        val zeitpunkt: LocalDateTime,
        val wetterId: UUID
    )

    data class LuftdruckDto(
        val minimum: Float,
        val maximum: Float,
        val zeitpunkt: LocalDateTime,
        val wetterId: UUID
    )

    data class WindrichtungDto(
        val minimum: Int,
        val maximum: Int,
        val zeitpunkt: LocalDateTime,
        val wetterId: UUID
    )

    data class WindgeschwindigkeitDto(
        val minimum: Int,
        val maximum: Int,
        val zeitpunkt: LocalDateTime,
        val wetterId: UUID
    )

    data class WindboeengeschwindigkeitDto(
        val minimum: Int,
        val maximum: Int,
        val zeitpunkt: LocalDateTime,
        val wetterId: UUID
    )

    data class SonnenstrahlungDto(
        val minimum: Float,
        val maximum: Float,
        val zeitpunkt: LocalDateTime,
        val wetterId: UUID
    )

    data class UvIndexDto(
        val minimum: Float,
        val maximum: Float,
        val zeitpunkt: LocalDateTime,
        val wetterId: UUID
    )

    data class NiederschlagDto(
        val minimum: Float,
        val maximum: Float,
        val zeitpunkt: LocalDateTime,
        val wetterId: UUID
    )

    data class NiederschlagGesamtDto(
        val minimum: Float,
        val maximum: Float,
        val zeitpunkt: LocalDateTime,
        val wetterId: UUID
    )
}