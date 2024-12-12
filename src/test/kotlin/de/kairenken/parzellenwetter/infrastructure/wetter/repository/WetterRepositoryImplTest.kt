package de.kairenken.parzellenwetter.infrastructure.wetter.repository

import de.kairenken.parzellenwetter.domain.wetter.wetterFixture
import de.kairenken.parzellenwetter.testcontainers.AbstractDatabaseTest
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class WetterRepositoryImplTest : AbstractDatabaseTest() {

    @Test
    fun `speichere Wetter`() {
        wetterRepositoryImplToTest.speichereWetter(wetterFixture)

        wetterJpaRepository.count() shouldBe 1
        wetterJpaRepository.findAll().first() shouldBe wetterEntityFixture
    }
}