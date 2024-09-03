package de.kairenken.parzellenwetter.infrastructure.wetter.weather

import de.kairenken.parzellenwetter.domain.wetter.Wetter
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

@Service
class WeatherClient(private val weatherProperties: WeatherProperties) {

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    private val jsonSerde = Json {
        ignoreUnknownKeys = true
    }

    fun fetchWeatherData(): Wetter {
        val request: HttpRequest = HttpRequest.newBuilder()
            .uri(URI(weatherProperties.url))
            .GET()
            .build()

        val client = HttpClient.newHttpClient()

        val responseBody = try {
            JSONObject(
                client.send(request, BodyHandlers.ofString()).body()
            )
                .getJSONArray("observations")
                .getJSONObject(0)
                .toString()
        } catch (exception: Exception) {
            throw FetchOfWeatherDataFailedException()
        }

        val wetter: WeatherDto = jsonSerde.decodeFromString(responseBody)

        return wetter.mapToDomain()
    }

    private fun WeatherDto.mapToDomain() = Wetter(
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

class FetchOfWeatherDataFailedException() : Exception()