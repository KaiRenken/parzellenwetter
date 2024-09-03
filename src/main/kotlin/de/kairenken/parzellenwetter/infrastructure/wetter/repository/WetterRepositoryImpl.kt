package de.kairenken.parzellenwetter.infrastructure.wetter.repository

import de.kairenken.parzellenwetter.domain.wetter.Wetter
import de.kairenken.parzellenwetter.domain.wetter.WetterRepository
import de.kairenken.parzellenwetter.infrastructure.wetter.weather.WeatherClient
import java.time.LocalDateTime
import org.springframework.stereotype.Service

@Service
class WetterRepositoryImpl(
    private val wetterJpaRepository: WetterJpaRepository,
    private val weatherDataCrawler: WeatherClient
) : WetterRepository {

    override fun holeWetter(von: LocalDateTime, bis: LocalDateTime): List<Wetter> =
        wetterJpaRepository
            .findAllByZeitpunktBetweenOrderByZeitpunkt(from = von, to = bis)
            .map { it.toDomain() }

    override fun holeAktuellesWetter(): Wetter = weatherDataCrawler.fetchWeatherData()

    override fun speichereWetter(wetter: Wetter) {
        wetterJpaRepository.save(wetter.mapToEntity())
    }

    private fun WetterEntity.toDomain(): Wetter = Wetter(
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

    private fun Wetter.mapToEntity() = WetterEntity(
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
