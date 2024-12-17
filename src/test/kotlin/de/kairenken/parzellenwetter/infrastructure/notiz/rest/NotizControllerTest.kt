package de.kairenken.parzellenwetter.infrastructure.notiz.rest

import com.ninjasquad.springmockk.MockkBean
import de.kairenken.parzellenwetter.application.notiz.NotizErstellung
import de.kairenken.parzellenwetter.domain.notiz.NotizRepository
import de.kairenken.parzellenwetter.domain.notiz.notizFixture
import de.kairenken.parzellenwetter.domain.notiz.notizNachrichtFixture
import de.kairenken.parzellenwetter.domain.notiz.notizVerfasserFixture
import io.mockk.every
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [NotizController::class])
internal class NotizControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var notizRepositoryMock: NotizRepository

    @MockkBean
    private lateinit var notizErstellungMock: NotizErstellung

    @Nested
    @DisplayName("Erstelle Notiz")
    inner class ErstelleNotizTest {

        @Test
        fun erfolgreich() {
            every {
                notizErstellungMock.erstelleNotiz(
                    verfasser = notizVerfasserFixture.value,
                    nachricht = notizNachrichtFixture.value
                )
            } returns NotizErstellung.NotizErstellt(notizFixture)

            mockMvc.perform(
                post("/api/notiz/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        createNotizDtoFixture.toJson()
                    )
            )
                .andExpect(status().isOk)
                .andExpect(content().json(readNotizDtoFixture.toJson()))
        }

        @Test
        fun `ohne Daten`() {
            every {
                notizRepositoryMock.holeAlle()
            } returns emptyList()


            mockMvc.get(
                "/api/notiz/"
            )
                .andExpect {
                    status { isOk() }
                    content { string("[]") }
                }
        }
    }

    @Nested
    @DisplayName("Hole alle Notizen")
    inner class HoleNotizenTest {

        @Test
        fun erfolgreich() {
            every {
                notizRepositoryMock.holeAlle()
            } returns listOf(notizFixture)

            mockMvc.get(
                "/api/notiz/"
            )
                .andExpect {
                    status { isOk() }
                    content { json("[${readNotizDtoFixture.toJson()}]") }
                }
        }

        @Test
        fun `ohne Daten`() {
            every {
                notizRepositoryMock.holeAlle()
            } returns emptyList()


            mockMvc.get(
                "/api/notiz/"
            )
                .andExpect {
                    status { isOk() }
                    content { string("[]") }
                }
        }
    }
}