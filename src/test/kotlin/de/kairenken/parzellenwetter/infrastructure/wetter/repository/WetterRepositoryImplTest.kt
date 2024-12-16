package de.kairenken.parzellenwetter.infrastructure.wetter.repository

import de.kairenken.parzellenwetter.domain.wetter.wetterFixture
import de.kairenken.parzellenwetter.domain.wetter.wetterIdFixture
import de.kairenken.parzellenwetter.testcontainers.AbstractDatabaseTest
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.mockk.every
import java.util.UUID
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class WetterRepositoryImplTest : AbstractDatabaseTest() {

    @Test
    fun `speichere Wetter`() {
        wetterRepositoryImplToTest.speichereWetter(wetterFixture)

        wetterJpaRepository.count() shouldBe 1
        wetterJpaRepository.findAll().first() shouldBe wetterEntityFixture
    }

    @Test
    fun `hole aktuelles Wetter`() {
        every { wetterClientMock.fetchWetterData() } returns wetterFixture

        wetterRepositoryImplToTest.holeAktuellesWetter() shouldBe wetterFixture
    }

    @Nested
    @DisplayName("Hole Wetter")
    inner class HoleWetterTest {

        @Test
        fun erfolgreich() {
            val wetterToIgnore1 = wetterEntityFixture
            val wetterToIgnore2 = wetterEntityFixture.copy(
                id = UUID.randomUUID(),
                zeitpunkt = wetterEntityFixture.zeitpunkt.plusMinutes(6L)
            )
            val wetterToFind1 = wetterEntityFixture.copy(
                id = UUID.randomUUID(),
                zeitpunkt = wetterEntityFixture.zeitpunkt.plusMinutes(2L)
            )
            val wetterToFind2 = wetterEntityFixture.copy(
                id = UUID.randomUUID(),
                zeitpunkt = wetterEntityFixture.zeitpunkt.plusMinutes(4L)
            )
            wetterJpaRepository.save(wetterToFind1)
            wetterJpaRepository.save(wetterToFind2)
            wetterJpaRepository.save(wetterToIgnore1)
            wetterJpaRepository.save(wetterToIgnore2)

            wetterRepositoryImplToTest.holeWetter(
                von = wetterEntityFixture.zeitpunkt.plusMinutes(1L),
                bis = wetterEntityFixture.zeitpunkt.plusMinutes(5L)
            ) shouldBe listOf(
                wetterFixture.copy(id = wetterIdFixture.copy(wetterToFind1.id), zeitpunkt = wetterToFind1.zeitpunkt),
                wetterFixture.copy(id = wetterIdFixture.copy(wetterToFind2.id), zeitpunkt = wetterToFind2.zeitpunkt)
            )
        }

        @Test
        fun `ohne Daten`() {
            wetterJpaRepository.save(wetterEntityFixture)

            wetterRepositoryImplToTest.holeWetter(
                von = wetterEntityFixture.zeitpunkt.plusMinutes(1L),
                bis = wetterEntityFixture.zeitpunkt.plusMinutes(5L)
            ).shouldBeEmpty()
        }
    }

    @Nested
    @DisplayName("Hole ExtremTemperatur")
    inner class HoleExtremTemperaturTest {

        @Test
        fun erfolgreich() {
            val wetterToIgnore1 = wetterEntityFixture.copy(
                temperatur = 0
            )
            val wetterToIgnore2 = wetterEntityFixture.copy(
                id = UUID.randomUUID(),
                temperatur = 3,
                zeitpunkt = wetterEntityFixture.zeitpunkt.plusMinutes(2L)
            )
            val wetterMaximumToFind = wetterEntityFixture.copy(
                temperatur = 9,
                id = UUID.randomUUID(),
                zeitpunkt = wetterEntityFixture.zeitpunkt.plusMinutes(4L)
            )
            val wetterMinimumToFind = wetterEntityFixture.copy(
                temperatur = 1,
                id = UUID.randomUUID(),
                zeitpunkt = wetterEntityFixture.zeitpunkt.plusMinutes(6L)
            )
            val wetterToIgnore3 = wetterEntityFixture.copy(
                temperatur = 4,
                id = UUID.randomUUID(),
                zeitpunkt = wetterEntityFixture.zeitpunkt.plusMinutes(8L)
            )
            val wetterToIgnore4 = wetterEntityFixture.copy(
                temperatur = 10,
                id = UUID.randomUUID(),
                zeitpunkt = wetterEntityFixture.zeitpunkt.plusMinutes(8L)
            )
            wetterJpaRepository.save(wetterMaximumToFind)
            wetterJpaRepository.save(wetterMinimumToFind)
            wetterJpaRepository.save(wetterToIgnore1)
            wetterJpaRepository.save(wetterToIgnore2)
            wetterJpaRepository.save(wetterToIgnore3)
            wetterJpaRepository.save(wetterToIgnore4)

            wetterRepositoryImplToTest.holeExtremTemperaturen(
                von = wetterEntityFixture.zeitpunkt.plusMinutes(3L),
                bis = wetterEntityFixture.zeitpunkt.plusMinutes(7L)
            ) shouldBe Pair(
                wetterFixture.copy(
                    id = wetterIdFixture.copy(wetterMinimumToFind.id),
                    zeitpunkt = wetterMinimumToFind.zeitpunkt,
                    temperatur = wetterMinimumToFind.temperatur
                ),
                wetterFixture.copy(
                    id = wetterIdFixture.copy(wetterMaximumToFind.id),
                    zeitpunkt = wetterMaximumToFind.zeitpunkt,
                    temperatur = wetterMaximumToFind.temperatur
                )
            )
        }

        @Test
        fun `ohne Daten`() {
            wetterJpaRepository.save(wetterEntityFixture)

            wetterRepositoryImplToTest.holeExtremTemperaturen(
                von = wetterEntityFixture.zeitpunkt.plusMinutes(1L),
                bis = wetterEntityFixture.zeitpunkt.plusMinutes(5L)
            ) shouldBe Pair(null, null)
        }
    }
}