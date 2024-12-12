package de.kairenken.parzellenwetter.application.wetter

import de.kairenken.parzellenwetter.domain.wetter.WetterRepository
import de.kairenken.parzellenwetter.domain.wetter.wetterFixture
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class WetterUpdateTest {

    private val wetterRepositoryMock = mockk<WetterRepository>()

    private val wetterUpdateToTest = WetterUpdate(wetterRepositoryMock)

    @Test
    fun `update Wetter successfully`() {
        every { wetterRepositoryMock.holeAktuellesWetter() } returns wetterFixture
        every { wetterRepositoryMock.speichereWetter(any()) } just Runs

        wetterUpdateToTest.updateWetter()

        verify(exactly = 1) { wetterRepositoryMock.speichereWetter(wetterFixture) }
    }
}