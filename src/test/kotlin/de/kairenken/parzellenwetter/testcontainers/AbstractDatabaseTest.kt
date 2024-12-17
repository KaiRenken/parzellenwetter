package de.kairenken.parzellenwetter.testcontainers

import com.ninjasquad.springmockk.MockkBean
import de.kairenken.parzellenwetter.infrastructure.notiz.repository.NotizJpaRepository
import de.kairenken.parzellenwetter.infrastructure.notiz.repository.NotizRepositoryImpl
import de.kairenken.parzellenwetter.infrastructure.wetter.client.WetterClient
import de.kairenken.parzellenwetter.infrastructure.wetter.repository.WetterJpaRepository
import de.kairenken.parzellenwetter.infrastructure.wetter.repository.WetterRepositoryImpl
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = [PostgresContextInitializer::class])
@Import(
    value = [
        WetterRepositoryImpl::class,
        NotizRepositoryImpl::class
    ]
)
abstract class AbstractDatabaseTest {

    @MockkBean
    protected lateinit var wetterClientMock: WetterClient

    @Autowired
    protected lateinit var wetterJpaRepository: WetterJpaRepository

    @Autowired
    protected lateinit var notizJpaRepository: NotizJpaRepository

    @BeforeEach
    fun setUp() {
        wetterJpaRepository.deleteAll()
        notizJpaRepository.deleteAll()
    }
}