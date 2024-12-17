package de.kairenken.parzellenwetter.domain.notiz

import de.kairenken.parzellenwetter.domain.common.Erzeugt
import java.time.LocalDateTime
import java.util.UUID

val notizIdFixture = Notiz.Id(UUID.fromString("fef2ec30-5ec3-413b-bfe2-ff2b0dc7302c"))

val notizVerfasserFixture = (Notiz.Verfasser(
    "test-notiz-verfasser"
) as Erzeugt).value

val notizNachrichtFixture = (Notiz.Nachricht(
    "test-notiz-nachricht"
) as Erzeugt).value

val notizZeitpunktFixture = Notiz.Zeitpunkt(LocalDateTime.parse("2024-12-17T14:45:12"))

val notizFixture = Notiz(
    id = notizIdFixture,
    verfasser = notizVerfasserFixture,
    nachricht = notizNachrichtFixture,
    zeitpunkt = notizZeitpunktFixture
)