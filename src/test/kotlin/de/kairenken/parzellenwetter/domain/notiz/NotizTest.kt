package de.kairenken.parzellenwetter.domain.notiz

import de.kairenken.parzellenwetter.domain.common.Erzeugt
import de.kairenken.parzellenwetter.domain.common.UngueltigeArgumente
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Erzeuge Notiz")
internal class NotizTest {

    @Test
    fun erfolgreich() {
        val notiz = Notiz(
            verfasser = notizVerfasserFixture.value,
            nachricht = notizNachrichtFixture.value
        )

        notiz.shouldBeInstanceOf<Erzeugt<Notiz>>()
            .value shouldBe notizFixture.copy(
            id = notiz.value.id,
            zeitpunkt = notiz.value.zeitpunkt
        )
    }

    @Test
    fun `mit ungueltigen Argumenten`() {
        val notiz = Notiz(
            verfasser = "",
            nachricht = ""
        )

        notiz.shouldBeInstanceOf<UngueltigeArgumente>()
            .messages.shouldContainExactlyInAnyOrder(
                "Notiz.Verfasser darf nicht leer sein",
                "Notiz.Nachricht darf nicht leer sein"
            )
    }
}