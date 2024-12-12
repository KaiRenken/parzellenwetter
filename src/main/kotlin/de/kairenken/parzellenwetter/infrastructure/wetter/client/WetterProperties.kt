package de.kairenken.parzellenwetter.infrastructure.wetter.client

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "wetter")
class WetterProperties(
    var url: String
)