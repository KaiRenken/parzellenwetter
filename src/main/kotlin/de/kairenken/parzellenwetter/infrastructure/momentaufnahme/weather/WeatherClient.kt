package de.kairenken.parzellenwetter.infrastructure.momentaufnahme.weather

import de.kairenken.parzellenwetter.domain.momentaufnahme.Momentaufnahme
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
class WeatherClient() {

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    private val jsonSerde = Json {
        ignoreUnknownKeys = true
    }

    fun fetchWeatherData(): Momentaufnahme {
        val request: HttpRequest = HttpRequest.newBuilder()
            .uri(URI(WEATHER_DATA_URL))
            .GET()
            .build()

        val client = HttpClient.newHttpClient()

        val responseBody = JSONObject(
            client.send(request, BodyHandlers.ofString()).body()
        )
            .getJSONArray("observations")
            .getJSONObject(0)
            .toString()

        val momentaufnahme: WeatherDto = jsonSerde.decodeFromString(responseBody)

        return momentaufnahme.mapToDomain()
    }

    private fun WeatherDto.mapToDomain() = Momentaufnahme(
        id = UUID.randomUUID(),
        zeitpunkt = LocalDateTime.parse(this.obsTimeLocal, dateTimeFormatter),
        sonnenstrahlung = this.solarRadiation,
        uvIndex = this.uv,
        windrichtung = this.winddir,
        luftfeuchtigkeit = this.humidity,
        temperatur = this.metric?.temp,
        taupunkt = this.metric?.dewpt,
        windgeschwindigkeit = this.metric?.windSpeed,
        windboeengeschwindigkeit = this.metric?.windGust,
        luftdruck = this.metric?.pressure,
        niederschlag = this.metric?.precipRate,
        niederschlagGesamt = this.metric?.precipTotal
    )
}
