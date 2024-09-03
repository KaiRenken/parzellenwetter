package de.kairenken.parzellenwetter.it

import de.kairenken.parzellenwetter.application.wetter.WetterUpdate
import de.kairenken.parzellenwetter.testcontainers.AbstractIntegrationTest
import java.time.LocalDateTime
import org.junit.jupiter.api.Test
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
        // Prepare
        val now = LocalDateTime.now()
        wetterUpdate.updateWetter()

        // Act & assert
        val content = mockMvc.get("/api/wetter/?from=${now.minusSeconds(30L)}&to=${now.plusSeconds(30L)}")
            .andExpect {
                status { isOk() }
            }
            .andReturn()
            .response
            .contentAsString

        println(content)
    }

    @Test
    fun `import and fetch wetter with some values null`() {

    }

    @Test
    fun `import wettter when server is not available`() {

    }
}