package de.kairenken.parzellenwetter.infrastructure.wetter.rest

import com.ninjasquad.springmockk.MockkBean
import de.kairenken.parzellenwetter.application.wetter.ExtremwertSuche
import de.kairenken.parzellenwetter.application.wetter.dto.ExtremwerteDto
import de.kairenken.parzellenwetter.application.wetter.extremwerteDtoFixture
import de.kairenken.parzellenwetter.domain.wetter.WetterRepository
import de.kairenken.parzellenwetter.domain.wetter.wetterFixture
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(controllers = [WetterController::class])
internal class WetterControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var wetterRepositoryMock: WetterRepository

    @MockkBean
    private lateinit var extremwertSucheMock: ExtremwertSuche

    @Nested
    @DisplayName("Hole Wetter")
    inner class HoleWetterTest {

        @Test
        fun erfolgreich() {
            val now = wetterFixture.zeitpunkt
            every {
                wetterRepositoryMock.holeWetter(
                    von = now.minusSeconds(30L),
                    bis = now.plusSeconds(30L)
                )
            } returns listOf(wetterFixture)

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

            record.getString("zeitpunkt") shouldBe wetterFixture.zeitpunkt.toString()
            record.getInt("temperatur") shouldBe wetterFixture.temperatur
            record.getInt("luftfeuchtigkeit") shouldBe wetterFixture.luftfeuchtigkeit
            record.getInt("taupunkt") shouldBe wetterFixture.taupunkt
            record.getFloat("luftdruck") shouldBe wetterFixture.luftdruck
            record.getInt("windrichtung") shouldBe wetterFixture.windrichtung
            record.getInt("windgeschwindigkeit") shouldBe wetterFixture.windgeschwindigkeit
            record.getInt("windboeengeschwindigkeit") shouldBe wetterFixture.windboeengeschwindigkeit
            record.getFloat("sonnenstrahlung") shouldBe wetterFixture.sonnenstrahlung
            record.getFloat("uvIndex") shouldBe wetterFixture.uvIndex
            record.getFloat("niederschlag") shouldBe wetterFixture.niederschlag
            record.getFloat("niederschlagGesamt") shouldBe wetterFixture.niederschlagGesamt
        }

        @Test
        fun `ohne Daten`() {
            val now = wetterFixture.zeitpunkt
            every {
                wetterRepositoryMock.holeWetter(
                    von = now.minusSeconds(30L),
                    bis = now.plusSeconds(30L)
                )
            } returns emptyList()

            mockMvc.get(
                "/api/wetter/?from=${now.minusHours(2L).minusSeconds(30L)}&to=${
                    now.minusHours(2L).plusSeconds(30L)
                }"
            )
                .andExpect {
                    status { isOk() }
                    content { string("[]") }
                }
        }
    }

    @Nested
    @DisplayName("Hole Extremwerte")
    inner class HoleExtremwerteTest {

        @Test
        fun erfolgreich() {
            val now = wetterFixture.zeitpunkt
            every {
                extremwertSucheMock.holeExtremwerte(
                    von = now.minusSeconds(30L),
                    bis = now.plusSeconds(30L)
                )
            } returns extremwerteDtoFixture

            val record = JSONObject(
                mockMvc.get(
                    "/api/wetter/extremwerte/?from=${now.minusHours(2L).minusSeconds(30L)}&to=${
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

            record.getJSONObject("temperatur").getJSONObject("minimum")
                .getInt("wert") shouldBe extremwerteDtoFixture.extremTemperaturen!!.minimum.wert
            record.getJSONObject("temperatur").getJSONObject("minimum")
                .getString("zeitpunkt") shouldBe extremwerteDtoFixture.extremTemperaturen!!.minimum.zeitpunkt.toString()
            record.getJSONObject("temperatur").getJSONObject("minimum")
                .getString("wetterId") shouldBe extremwerteDtoFixture.extremTemperaturen!!.minimum.wetterId.value.toString()
            record.getJSONObject("temperatur").getJSONObject("maximum")
                .getInt("wert") shouldBe extremwerteDtoFixture.extremTemperaturen!!.maximum.wert
            record.getJSONObject("temperatur").getJSONObject("maximum")
                .getString("zeitpunkt") shouldBe extremwerteDtoFixture.extremTemperaturen!!.maximum.zeitpunkt.toString()
            record.getJSONObject("temperatur").getJSONObject("maximum")
                .getString("wetterId") shouldBe extremwerteDtoFixture.extremTemperaturen!!.maximum.wetterId.value.toString()
        }

        @Test
        fun `ohne Daten`() {
            val now = wetterFixture.zeitpunkt
            every {
                extremwertSucheMock.holeExtremwerte(
                    von = now.minusSeconds(30L),
                    bis = now.plusSeconds(30L)
                )
            } returns ExtremwerteDto(null)

            val record = JSONObject(
                mockMvc.get(
                    "/api/wetter/extremwerte/?from=${now.minusHours(2L).minusSeconds(30L)}&to=${
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

            record.get("temperatur") shouldBe null
        }
    }
}