package de.kairenken.parzellenwetter.testcontainers

import de.kairenken.parzellenwetter.infrastructure.wetter.client.WetterClient
import de.kairenken.parzellenwetter.infrastructure.wetter.client.WetterProperties
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockserver.client.MockServerClient
import org.mockserver.junit.jupiter.MockServerExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(
    classes = [
        WetterClient::class,
        WetterProperties::class,
        String::class
    ]
)
@ExtendWith(MockServerExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("wetter")
abstract class AbstractClientTest {

    @Autowired
    protected lateinit var wetterProperties: WetterProperties

    protected lateinit var mockServerClient: MockServerClient

    @BeforeAll
    fun beforeAll(mockServerClient: MockServerClient) {
        this.mockServerClient = mockServerClient
        wetterProperties.url = "http://${mockServerClient.remoteAddress().hostString}:${mockServerClient.port}"
    }

    @BeforeEach
    fun setUp() {
        mockServerClient.reset()
    }
}