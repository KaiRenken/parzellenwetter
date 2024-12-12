package de.kairenken.parzellenwetter.testcontainers

import de.kairenken.parzellenwetter.infrastructure.wetter.client.WetterProperties
import de.kairenken.parzellenwetter.infrastructure.wetter.repository.WetterJpaRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockserver.client.MockServerClient
import org.mockserver.junit.jupiter.MockServerExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ExtendWith(MockServerExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("it")
@ContextConfiguration(initializers = [PostgresContextInitializer::class])
abstract class AbstractIntegrationTest {

    @Autowired
    protected lateinit var wetterJpaRepository: WetterJpaRepository

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
        wetterJpaRepository.deleteAll()
    }
}