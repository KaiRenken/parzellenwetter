package de.kairenken.parzellenwetter.domain.notiz

import de.kairenken.parzellenwetter.domain.common.Erzeugt
import de.kairenken.parzellenwetter.domain.common.UngueltigeArgumente
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("Erzeuge Notiz.Nachricht")
internal class NotizNachrichtTest {

    @Test
    fun erfolgreich() {
        val notizNachricht = Notiz.Nachricht(
            notizNachrichtFixture.value
        )

        notizNachricht.shouldBeInstanceOf<Erzeugt<Notiz.Nachricht>>()
            .value shouldBe notizNachrichtFixture
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "   "])
    fun `mit leerem value`(value: String) {
        val notizNachricht = Notiz.Nachricht(value)

        notizNachricht.shouldBeInstanceOf<UngueltigeArgumente>()
            .messages.shouldContainExactlyInAnyOrder(
                "Notiz.Nachricht darf nicht leer sein"
            )
    }

    @Test
    fun `mit zu langem value`() {
        val notizNachricht = Notiz.Nachricht("A".repeat(1001))

        notizNachricht.shouldBeInstanceOf<UngueltigeArgumente>()
            .messages.shouldContainExactlyInAnyOrder(
                "Notiz.Nachricht hat 1001 Zeichen, darf aber höchstens 1000 haben."
            )
    }
}