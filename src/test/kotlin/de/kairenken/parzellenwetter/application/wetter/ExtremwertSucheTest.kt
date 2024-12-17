package de.kairenken.parzellenwetter.application.wetter

import de.kairenken.parzellenwetter.domain.wetter.WetterRepository
import de.kairenken.parzellenwetter.domain.wetter.wetterFixture
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

internal class ExtremwertSucheTest {

    private val wetterRepositoryMock = mockk<WetterRepository>()

    private val extremwertSucheToTest = ExtremwertSuche(wetterRepositoryMock)

    @Test
    fun `hole Extremwerte`() {
        every {
            wetterRepositoryMock.holeExtremTemperaturen(
                von = wetterFixture.zeitpunkt.minusMinutes(1L),
                bis = wetterFixture.zeitpunkt.plusMinutes(1L)
            )
        } returns Pair(wetterFixture, wetterFixture)

        extremwertSucheToTest.holeExtremwerte(
            von = wetterFixture.zeitpunkt.minusMinutes(1L),
            bis = wetterFixture.zeitpunkt.plusMinutes(1L)
        ) shouldBe extremwerteDtoFixture
    }
}