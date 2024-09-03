package de.kairenken.parzellenwetter.it

import de.kairenken.parzellenwetter.application.wetter.WetterUpdate
import de.kairenken.parzellenwetter.infrastructure.wetter.weather.FetchOfWeatherDataFailedException
import de.kairenken.parzellenwetter.testcontainers.AbstractIntegrationTest
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime
import org.json.JSONArray
import org.junit.jupiter.api.Test
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@AutoConfigureMockMvc
class WetterIT : AbstractIntegrationTest() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var wetterUpdate: WetterUpdate

    @Test
    fun `import and fetch wetter successfully`() {
        val weatherApiResponse = """
            {
              "observations": [
                {
                  "stationID": "IBREME20",
                  "obsTimeUtc": "2024-09-03T13:43:11Z",
                  "obsTimeLocal": "2024-09-03 15:43:11",
                  "neighborhood": "Bremen",
                  "softwareType": "WH2650A_V1.7.5",
                  "country": "DE",
                  "solarRadiation": 536.6,
                  "lon": 8.829,
                  "realtimeFrequency": null,
                  "epoch": 1725370991,
                  "lat": 53.064999,
                  "uv": 5.0,
                  "winddir": 195,
                  "humidity": 62,
                  "qcStatus": 1,
                  "metric": {
                    "temp": 29,
                    "heatIndex": 32,
                    "dewpt": 21,
                    "windChill": 29,
                    "windSpeed": 16,
                    "windGust": 19,
                    "pressure": 1015.61,
                    "precipRate": 0.00,
                    "precipTotal": 0.00,
                    "elev": 6
                  }
                }
              ]
            }
        """.trimIndent()

        val now = LocalDateTime.parse("2024-09-03T15:43:11")

        mockServerClient.`when`(
            HttpRequest.request()
                .withMethod("GET")
        )
            .respond(HttpResponse.response().withStatusCode(200).withBody(weatherApiResponse))

        wetterUpdate.updateWetter()

        wetterJpaRepository.count() shouldBe 1
        val dbRecord = wetterJpaRepository.findAll()[0]

        val record = JSONArray(
            mockMvc.get(
                "/api/wetter/?from=${now.minusHours(2L).minusSeconds(30L)}&to=${
                    now.minusHours(2L).plusSeconds(30L)
                }"
            )
                .andExpect {
                    status { isOk() }
                }
                .andReturn()
                .response
                .contentAsString
        )
            .getJSONObject(0)

        record.getString("id") shouldBe dbRecord.id.toString()
        record.getString("zeitpunkt") shouldBe dbRecord.zeitpunkt.toString()
        record.getInt("temperatur") shouldBe dbRecord.temperatur
        record.getInt("luftfeuchtigkeit") shouldBe dbRecord.luftfeuchtigkeit
        record.getInt("taupunkt") shouldBe dbRecord.taupunkt
        record.getFloat("luftdruck") shouldBe dbRecord.luftdruck
        record.getInt("windrichtung") shouldBe dbRecord.windrichtung
        record.getInt("windgeschwindigkeit") shouldBe dbRecord.windgeschwindigkeit
        record.getInt("windboeengeschwindigkeit") shouldBe dbRecord.windboeengeschwindigkeit
        record.getFloat("sonnenstrahlung") shouldBe dbRecord.sonnenstrahlung
        record.getFloat("uvIndex") shouldBe dbRecord.uvIndex
        record.getFloat("niederschlag") shouldBe dbRecord.niederschlag
        record.getFloat("niederschlagGesamt") shouldBe dbRecord.niederschlagGesamt
    }

    @Test
    fun `import and fetch wetter with some values null`() {
        val weatherApiResponse = """
            {
              "observations": [
                {
                  "stationID": "IBREME20",
                  "obsTimeUtc": "2024-09-03T13:43:11Z",
                  "obsTimeLocal": "2024-09-03 15:43:11",
                  "neighborhood": "Bremen",
                  "softwareType": "WH2650A_V1.7.5",
                  "country": "DE",
                  "solarRadiation": null,
                  "lon": 8.829,
                  "realtimeFrequency": null,
                  "epoch": 1725370991,
                  "lat": 53.064999,
                  "uv": 5.0,
                  "winddir": 195,
                  "humidity": 62,
                  "qcStatus": 1,
                  "metric": {
                    "temp": 29,
                    "heatIndex": 32,
                    "dewpt": 21,
                    "windChill": 29,
                    "windSpeed": null,
                    "windGust": 19,
                    "pressure": 1015.61,
                    "precipRate": 0.00,
                    "precipTotal": null,
                    "elev": 6
                  }
                }
              ]
            }
        """.trimIndent()

        mockServerClient.`when`(
            HttpRequest.request()
                .withMethod("GET")
        )
            .respond(HttpResponse.response().withStatusCode(200).withBody(weatherApiResponse))

        wetterUpdate.updateWetter()

        wetterJpaRepository.count() shouldBe 1

        val now = LocalDateTime.parse("2024-09-03T15:43:11")

        val record = JSONArray(
            mockMvc.get(
                "/api/wetter/?from=${now.minusHours(2L).minusSeconds(30L)}&to=${
                    now.minusHours(2L).plusSeconds(30L)
                }"
            )
                .andExpect {
                    status { isOk() }
                }
                .andReturn()
                .response
                .contentAsString
        )
            .getJSONObject(0)

        record.getString("zeitpunkt") shouldBe "2024-09-03T15:43:11"
        record.getInt("temperatur") shouldBe 29
        record.getInt("luftfeuchtigkeit") shouldBe 62
        record.getInt("taupunkt") shouldBe 21
        record.getFloat("luftdruck") shouldBe 1015.61f
        record.getInt("windrichtung") shouldBe 195
        record.get("windgeschwindigkeit") shouldBe null
        record.getInt("windboeengeschwindigkeit") shouldBe 19
        record.get("sonnenstrahlung") shouldBe null
        record.getFloat("uvIndex") shouldBe 5.0f
        record.getFloat("niederschlag") shouldBe 0.00f
        record.get("niederschlagGesamt") shouldBe null
    }

    @Test
    fun `import wetter when server is not available`() {
        mockServerClient.`when`(
            HttpRequest.request()
                .withMethod("GET")
        )
            .respond(HttpResponse.response().withStatusCode(500))

        shouldThrowExactly<FetchOfWeatherDataFailedException> { wetterUpdate.updateWetter() }

        wetterJpaRepository.count() shouldBe 0
    }
}