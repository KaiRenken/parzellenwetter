package de.kairenken.parzellenwetter.infrastructure.wetter.client

import de.kairenken.parzellenwetter.domain.wetter.wetterFixture
import de.kairenken.parzellenwetter.testcontainers.AbstractClientTest
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.springframework.beans.factory.annotation.Autowired

@DisplayName("Fetch Wetter data")
internal class WetterClientTest : AbstractClientTest() {

    @Autowired
    private lateinit var wetterClientToTest: WetterClient

    @Test
    fun successfully() {
        val wetterApiResponse = """
            {
              "observations": [
                {
                  "stationID": "IBREME20",
                  "obsTimeUtc": "2024-09-03T13:43:11Z",
                  "obsTimeLocal": "2024-09-03 15:43:11",
                  "neighborhood": "Bremen",
                  "softwareType": "WH2650A_V1.7.5",
                  "country": "DE",
                  "solarRadiation": 45.7,
                  "lon": 8.829,
                  "realtimeFrequency": null,
                  "epoch": 1725370991,
                  "lat": 53.064999,
                  "uv": 3.5,
                  "winddir": 145,
                  "humidity": 67,
                  "qcStatus": 1,
                  "metric": {
                    "temp": 23,
                    "heatIndex": 32,
                    "dewpt": 15,
                    "windChill": 29,
                    "windSpeed": 60,
                    "windGust": 70,
                    "pressure": 1099.9,
                    "precipRate": 6.9,
                    "precipTotal": 16.4,
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
            .respond(HttpResponse.response().withStatusCode(200).withBody(wetterApiResponse))

        val result = wetterClientToTest.fetchWetterData()
        result shouldBe wetterFixture.copy(id = result.id)
    }

    @Test
    fun `with some values null`() {
        val wetterApiResponse = """
            {
              "observations": [
                {
                  "stationID": "IBREME20",
                  "obsTimeUtc": "2024-09-03T13:43:11Z",
                  "obsTimeLocal": "2024-09-03 15:43:11",
                  "neighborhood": "Bremen",
                  "softwareType": "WH2650A_V1.7.5",
                  "country": "DE",
                  "solarRadiation": 45.7,
                  "lon": 8.829,
                  "realtimeFrequency": null,
                  "epoch": 1725370991,
                  "lat": 53.064999,
                  "uv": null,
                  "winddir": null,
                  "humidity": 67,
                  "qcStatus": 1,
                  "metric": {
                    "temp": 23,
                    "heatIndex": 32,
                    "dewpt": null,
                    "windChill": 29,
                    "windSpeed": 60,
                    "windGust": 70,
                    "pressure": 1099.9,
                    "precipRate": 6.9,
                    "precipTotal": 16.4,
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
            .respond(HttpResponse.response().withStatusCode(200).withBody(wetterApiResponse))

        val result = wetterClientToTest.fetchWetterData()
        result shouldBe wetterFixture.copy(id = result.id, uvIndex = null, windrichtung = null, taupunkt = null)
    }

    @Test
    fun `with server not available`() {
        mockServerClient.`when`(
            HttpRequest.request()
                .withMethod("GET")
        )
            .respond(HttpResponse.response().withStatusCode(500))

        shouldThrowExactly<FetchOfWetterDataFailedException> { wetterClientToTest.fetchWetterData() }
    }
}