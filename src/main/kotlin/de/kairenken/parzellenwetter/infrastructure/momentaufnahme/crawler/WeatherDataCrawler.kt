package de.kairenken.parzellenwetter.infrastructure.momentaufnahme.crawler

import de.kairenken.parzellenwetter.infrastructure.momentaufnahme.repository.MomentaufnahmeEntity
import de.kairenken.parzellenwetter.infrastructure.momentaufnahme.repository.MomentaufnahmeJpaRepository
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import kotlinx.serialization.json.Json
import org.json.JSONObject
import org.springframework.stereotype.Service


private const val WEATHER_DATA_URL =
    "https://api.weather.com/v2/pws/observations/current?apiKey=7d73d43202e5423bb3d43202e5123ba4&stationId=IBREME20&format=json&units=m"

@Service
class WeatherDataCrawler(private val momentaufnahmeJpaRepository: MomentaufnahmeJpaRepository) {

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    private val jsonSerde = Json {
        ignoreUnknownKeys = true
    }

    fun updateWeatherData() {
        val request: HttpRequest = HttpRequest.newBuilder()
            .uri(URI(WEATHER_DATA_URL))
            .GET()
            .build()

        val client = HttpClient.newHttpClient()

        val response = JSONObject(
            client.send(request, BodyHandlers.ofString()).body()
        )
            .getJSONArray("observations")
            .getJSONObject(0)
            .toString()

        val momentaufnahme: ImportMomentaufnahmeDto = jsonSerde.decodeFromString(response)

        momentaufnahmeJpaRepository.save(momentaufnahme.mapToEntity())
    }

    private fun ImportMomentaufnahmeDto.mapToEntity(): MomentaufnahmeEntity = MomentaufnahmeEntity(
        id = UUID.randomUUID(),
        obsTimeLocal = LocalDateTime.parse(this.obsTimeLocal, dateTimeFormatter),
        solarRadiation = this.solarRadiation,
        uv = this.uv,
        winddir = this.winddir,
        humidity = this.humidity,
        temp = this.metric?.temp,
        dewpt = this.metric?.dewpt,
        windSpeed = this.metric?.windSpeed,
        windGust = this.metric?.windGust,
        pressure = this.metric?.pressure,
        precipRate = this.metric?.precipRate,
        precipTotal = this.metric?.precipTotal
    )
}
