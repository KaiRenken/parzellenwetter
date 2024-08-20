package de.kairenken.parzellenwetter.it

import de.kairenken.parzellenwetter.application.momentaufnahme.MomentaufnahmenUpdate
import de.kairenken.parzellenwetter.testcontainers.AbstractIntegrationTest
import java.time.LocalDateTime
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@AutoConfigureMockMvc
class MomentaufnahmeIT : AbstractIntegrationTest() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var momentaufnahmenUpdate: MomentaufnahmenUpdate

    @Test
    fun `import and fetch momentaufnahmen successfully`() {
        // Prepare
        val now = LocalDateTime.now()
        momentaufnahmenUpdate.updateMomentaufnahmen()

        // Act & assert
        val content = mockMvc.get("/api/momentaufnahme/?from=${now.minusSeconds(30L)}&to=${now.plusSeconds(30L)}")
            .andExpect {
                status { isOk() }
            }
            .andReturn()
            .response
            .contentAsString

        println(content)
    }

    @Test
    fun `import and fetch momentaufnahmen with some values null`() {

    }

    @Test
    fun `import momentaufnahmen when server is not available`() {

    }
}