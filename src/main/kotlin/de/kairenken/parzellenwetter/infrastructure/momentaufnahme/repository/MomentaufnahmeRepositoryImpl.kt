package de.kairenken.parzellenwetter.infrastructure.momentaufnahme.repository

import de.kairenken.parzellenwetter.domain.momentaufnahme.Momentaufnahme
import de.kairenken.parzellenwetter.domain.momentaufnahme.MomentaufnahmeRepository
import java.time.LocalDateTime
import org.springframework.stereotype.Service

@Service
class MomentaufnahmeRepositoryImpl(private val momentaufnahmeJpaRepository: MomentaufnahmeJpaRepository) :
    MomentaufnahmeRepository {

    override fun holeMomentaufnahmen(von: LocalDateTime, bis: LocalDateTime): List<Momentaufnahme> =
        momentaufnahmeJpaRepository
            .findAllByObsTimeLocalBetweenOrderByObsTimeLocal(from = von, to = bis)
            .map { it.toDomain() }

    private fun MomentaufnahmeEntity.toDomain(): Momentaufnahme = Momentaufnahme(
        id = this.id,
        zeitpunkt = this.obsTimeLocal,
        temperatur = this.temp,
        luftfeuchtigkeit = this.humidity,
        taupunkt = this.dewpt,
        luftdruck = this.pressure,
        windrichtung = this.winddir,
        windgeschwindigkeit = this.windSpeed,
        windboeengeschwindigkeit = this.windGust,
        sonnenstrahlung = this.solarRadiation,
        uvIndex = this.uv,
        niederschlag = this.precipRate,
        niederschlagDurchschnitt = this.precipTotal
    )
}
