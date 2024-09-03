package de.kairenken.parzellenwetter

import de.kairenken.parzellenwetter.infrastructure.wetter.weather.WeatherProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EnableConfigurationProperties(WeatherProperties::class)
@SpringBootApplication
class ParzellenwetterApplication

fun main(args: Array<String>) {
    runApplication<ParzellenwetterApplication>(*args)
}
