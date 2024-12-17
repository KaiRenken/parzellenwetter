package de.kairenken.parzellenwetter.infrastructure.notiz.repository

import de.kairenken.parzellenwetter.domain.notiz.notizIdFixture
import de.kairenken.parzellenwetter.domain.notiz.notizNachrichtFixture
import de.kairenken.parzellenwetter.domain.notiz.notizVerfasserFixture
import de.kairenken.parzellenwetter.domain.notiz.notizZeitpunktFixture
import de.kairenken.parzellenwetter.infrastructure.notiz.repository.entity.NotizEntity

val notizEntityFixture = NotizEntity(
    id = notizIdFixture.value,
    verfasser = notizVerfasserFixture.value,
    nachricht = notizNachrichtFixture.value,
    zeitpunkt = notizZeitpunktFixture.value
)