package de.kairenken.parzellenwetter.infrastructure.momentaufnahme.schedule

import de.kairenken.parzellenwetter.application.momentaufnahme.MomentaufnahmenUpdate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class UpdateScheduler(private val momentaufnahmenUpdate: MomentaufnahmenUpdate) {

    @Scheduled(cron = "1 * * * * *")
    fun scheduleUpdateWeatherData() {
        momentaufnahmenUpdate.updateMomentaufnahmen()
    }
}