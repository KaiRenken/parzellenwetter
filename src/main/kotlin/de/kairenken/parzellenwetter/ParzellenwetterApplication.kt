package de.kairenken.parzellenwetter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class ParzellenwetterApplication

fun main(args: Array<String>) {
    runApplication<ParzellenwetterApplication>(*args)
}
