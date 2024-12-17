package de.kairenken.parzellenwetter.infrastructure.notiz.repository

import de.kairenken.parzellenwetter.domain.notiz.notizFixture
import de.kairenken.parzellenwetter.infrastructure.notiz.NotizRepositoryImpl
import de.kairenken.parzellenwetter.testcontainers.AbstractDatabaseTest
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class NotizRepositoryImplTest : AbstractDatabaseTest() {

    @Autowired
    protected lateinit var notizRepositoryImplToTest: NotizRepositoryImpl

    @Test
    fun `speichere Notiz`() {
        notizRepositoryImplToTest.speichere(notizFixture)

        notizJpaRepository.count() shouldBe 1

        notizJpaRepository.findAll().first() shouldBe notizEntityFixture
    }
}