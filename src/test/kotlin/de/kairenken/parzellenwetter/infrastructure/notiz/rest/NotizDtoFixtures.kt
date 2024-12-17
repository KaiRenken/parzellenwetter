package de.kairenken.parzellenwetter.infrastructure.notiz.rest

import de.kairenken.parzellenwetter.domain.notiz.notizIdFixture
import de.kairenken.parzellenwetter.domain.notiz.notizNachrichtFixture
import de.kairenken.parzellenwetter.domain.notiz.notizVerfasserFixture
import de.kairenken.parzellenwetter.domain.notiz.notizZeitpunktFixture
import de.kairenken.parzellenwetter.infrastructure.notiz.rest.dto.CreateNotizDto
import de.kairenken.parzellenwetter.infrastructure.notiz.rest.dto.ReadNotizDto

val createNotizDtoFixture = CreateNotizDto(
    verfasser = notizVerfasserFixture.value,
    nachricht = notizNachrichtFixture.value
)

val readNotizDtoFixture = ReadNotizDto(
    id = notizIdFixture.value,
    verfasser = notizVerfasserFixture.value,
    nachricht = notizNachrichtFixture.value,
    zeitpunkt = notizZeitpunktFixture.value
)

fun CreateNotizDto.toJson() = """
    {
        "verfasser": "${this.verfasser}",
        "nachricht": "${this.nachricht}"
    }
""".trimIndent()

fun ReadNotizDto.toJson() = """
    {
        "id": "${this.id}",
        "verfasser": "${this.verfasser}",
        "nachricht": "${this.nachricht}",
        "zeitpunkt": "${this.zeitpunkt}"
    }
""".trimIndent()