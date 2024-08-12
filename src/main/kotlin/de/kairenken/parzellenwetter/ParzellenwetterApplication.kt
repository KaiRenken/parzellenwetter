package de.kairenken.parzellenwetter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ParzellenwetterApplication

fun main(args: Array<String>) {
    runApplication<ParzellenwetterApplication>(*args)
}
