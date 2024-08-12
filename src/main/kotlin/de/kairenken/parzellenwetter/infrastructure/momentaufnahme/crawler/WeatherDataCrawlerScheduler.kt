package de.kairenken.parzellenwetter.infrastructure.momentaufnahme.crawler

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class WeatherDataCrawlerScheduler(private val weatherDataCrawler: WeatherDataCrawler) {

    @Scheduled(cron = "1 * * * * *")
    fun scheduleUpdateWeatherData() {
        weatherDataCrawler.updateWeatherData()
    }
}