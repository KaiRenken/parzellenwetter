package de.kairenken.parzellenwetter.it

import de.kairenken.parzellenwetter.infrastructure.notiz.repository.notizEntityFixture
import de.kairenken.parzellenwetter.infrastructure.notiz.rest.createNotizDtoFixture
import de.kairenken.parzellenwetter.infrastructure.notiz.rest.readNotizDtoFixture
import de.kairenken.parzellenwetter.infrastructure.notiz.rest.toJson
import de.kairenken.parzellenwetter.testcontainers.AbstractIntegrationTest
import io.kotest.matchers.date.shouldBeWithin
import io.kotest.matchers.shouldBe
import java.time.Duration
import java.time.LocalDateTime
import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
class NotizIT : AbstractIntegrationTest() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `create Notiz`() {
        val result = JSONObject(
            mockMvc.perform(
                post("/api/notiz/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        createNotizDtoFixture.toJson()
                    )
            )
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString
        )

        notizJpaRepository.count() shouldBe 1
        val dbRecord = notizJpaRepository.findAll().first()

        result.getString("id") shouldBe dbRecord.id.toString()
        result.getString("verfasser") shouldBe createNotizDtoFixture.verfasser
        result.getString("nachricht") shouldBe createNotizDtoFixture.nachricht
        LocalDateTime.parse(result.getString("zeitpunkt")).shouldBeWithin(Duration.ofSeconds(1L), dbRecord.zeitpunkt)
    }

    @Test
    fun `fetch all Notizen`() {
        notizJpaRepository.save(notizEntityFixture)

        mockMvc.get(
            "/api/notiz/"
        )
            .andExpect {
                status { isOk() }
                content { json("[${readNotizDtoFixture.toJson()}]") }
            }
    }
}