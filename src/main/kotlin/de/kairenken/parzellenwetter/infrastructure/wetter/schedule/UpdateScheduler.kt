package de.kairenken.parzellenwetter.infrastructure.wetter.schedule

import de.kairenken.parzellenwetter.application.wetter.WetterUpdate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class UpdateScheduler(private val wetterUpdate: WetterUpdate) {

    @Scheduled(cron = "\${schedule.update.cron}")
    fun scheduleUpdateWeatherData() {
        wetterUpdate.updateWetter()
    }
}