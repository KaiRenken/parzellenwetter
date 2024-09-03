package de.kairenken.parzellenwetter.infrastructure.wetter.weather

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "weather")
class WeatherProperties(
    var url: String
)