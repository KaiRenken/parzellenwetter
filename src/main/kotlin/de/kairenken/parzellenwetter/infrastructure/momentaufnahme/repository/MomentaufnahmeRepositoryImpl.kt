package de.kairenken.parzellenwetter.infrastructure.momentaufnahme.repository

import de.kairenken.parzellenwetter.domain.momentaufnahme.Momentaufnahme
import de.kairenken.parzellenwetter.domain.momentaufnahme.MomentaufnahmeRepository
import de.kairenken.parzellenwetter.infrastructure.momentaufnahme.weather.WeatherClient
import java.time.LocalDateTime
import org.springframework.stereotype.Service

@Service
class MomentaufnahmeRepositoryImpl(
    private val momentaufnahmeJpaRepository: MomentaufnahmeJpaRepository,
    private val weatherDataCrawler: WeatherClient
) : MomentaufnahmeRepository {

    override fun holeMomentaufnahmen(von: LocalDateTime, bis: LocalDateTime): List<Momentaufnahme> =
        momentaufnahmeJpaRepository
            .findAllByZeitpunktBetweenOrderByZeitpunkt(from = von, to = bis)
            .map { it.toDomain() }

    override fun holeAktuelleMomentaufnahme(): Momentaufnahme = weatherDataCrawler.fetchWeatherData()

    override fun speichereMomentaufnahme(momentaufnahme: Momentaufnahme) {
        momentaufnahmeJpaRepository.save(momentaufnahme.mapToEntity())
    }

    private fun MomentaufnahmeEntity.toDomain(): Momentaufnahme = Momentaufnahme(
        id = this.id,
        zeitpunkt = this.zeitpunkt,
        temperatur = this.temperatur,
        luftfeuchtigkeit = this.luftfeuchtigkeit,
        taupunkt = this.taupunkt,
        luftdruck = this.luftdruck,
        windrichtung = this.windrichtung,
        windgeschwindigkeit = this.windgeschwindigkeit,
        windboeengeschwindigkeit = this.windboeengeschwindigkeit,
        sonnenstrahlung = this.sonnenstrahlung,
        uvIndex = this.uvIndex,
        niederschlag = this.niederschlag,
        niederschlagGesamt = this.niederschlagGesamt
    )

    private fun Momentaufnahme.mapToEntity() = MomentaufnahmeEntity(
        id = this.id,
        zeitpunkt = this.zeitpunkt,
        temperatur = this.temperatur,
        luftfeuchtigkeit = this.luftfeuchtigkeit,
        taupunkt = this.taupunkt,
        luftdruck = this.luftdruck,
        windrichtung = this.windrichtung,
        windgeschwindigkeit = this.windgeschwindigkeit,
        windboeengeschwindigkeit = this.windboeengeschwindigkeit,
        sonnenstrahlung = this.sonnenstrahlung,
        uvIndex = this.uvIndex,
        niederschlag = this.niederschlag,
        niederschlagGesamt = this.niederschlagGesamt
    )
}
