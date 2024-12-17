package de.kairenken.parzellenwetter.e2e

import de.kairenken.parzellenwetter.application.wetter.WetterUpdate
import de.kairenken.parzellenwetter.testcontainers.AbstractE2ETest
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime
import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@AutoConfigureMockMvc
class WetterE2ETest : AbstractE2ETest() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var wetterUpdate: WetterUpdate

    @Test
    fun `import and fetch wetter end to end`() {
        val now = LocalDateTime.now()

        wetterUpdate.updateWetter()

        wetterJpaRepository.count() shouldBe 1

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

        shouldNotThrowAny { record.get("id") }
        shouldNotThrowAny { record.get("zeitpunkt") }
        shouldNotThrowAny { record.get("temperatur") }
        shouldNotThrowAny { record.get("luftfeuchtigkeit") }
        shouldNotThrowAny { record.get("taupunkt") }
        shouldNotThrowAny { record.get("luftdruck") }
        shouldNotThrowAny { record.get("windrichtung") }
        shouldNotThrowAny { record.get("windgeschwindigkeit") }
        shouldNotThrowAny { record.get("windboeengeschwindigkeit") }
        shouldNotThrowAny { record.get("sonnenstrahlung") }
        shouldNotThrowAny { record.get("uvIndex") }
        shouldNotThrowAny { record.get("niederschlag") }
        shouldNotThrowAny { record.get("niederschlagGesamt") }
    }

    @Test
    fun `import and fetch extremwerte successfully`() {
        val now = LocalDateTime.now()

        wetterUpdate.updateWetter()

        wetterJpaRepository.count() shouldBe 1

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

        shouldNotThrowAny { record.getJSONObject("temperatur") }
    }
}