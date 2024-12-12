package de.kairenken.parzellenwetter.infrastructure.wetter.schedule

import de.kairenken.parzellenwetter.application.wetter.WetterUpdate
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class WetterUpdateSchedulerTest {

    private val wetterUpdateMock = mockk<WetterUpdate>()

    private val wetterUpdateSchedulerToTest = WetterUpdateScheduler(wetterUpdateMock)

    @Test
    fun `schedule update Wetter successfully`() {
        every { wetterUpdateMock.updateWetter() } just Runs

        wetterUpdateSchedulerToTest.scheduleUpdateWetterData()

        verify(exactly = 1) { wetterUpdateMock.updateWetter() }
    }
}