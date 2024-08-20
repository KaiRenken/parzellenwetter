package de.kairenken.parzellenwetter.testcontainers

import de.kairenken.parzellenwetter.infrastructure.momentaufnahme.repository.MomentaufnahmeJpaRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
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
    protected lateinit var momentaufnahmeJpaRepository: MomentaufnahmeJpaRepository

    @BeforeEach
    fun setUp() {
        momentaufnahmeJpaRepository.deleteAll()
    }
}